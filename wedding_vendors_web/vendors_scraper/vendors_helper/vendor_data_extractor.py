from bs4 import BeautifulSoup
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.by import By
from selenium.webdriver.support.wait import WebDriverWait
from selenium.webdriver.support import expected_conditions as ec
import os


import re
import pandas as pd
import json

from concurrent.futures import ThreadPoolExecutor
from threading import Thread

from vendors_scraper.vendors_helper.egypt_locations import egypt_locations_dict
import vendors_scraper.vendors_helper.wedding_vendors_scraper as wv_scraper
from vendors_scraper.vendors_helper.vendor_scraper_helper import VendorScraperHelper as VsHelper 

from vendors_scraper import models,serializers


class VendorDataExtractor:
    
    def __init__(self):
        self.extractor_registry={
            "Instagram":InstaVendorDataExtractor(),
            "ArabiawWebsite":ArabiawVendorDataExtractor()
        }

    def create_extractor(self,data_extractor):

        if data_extractor in self.extractor_registry:
            return self.extractor_registry[data_extractor]
        else:
            raise ValueError("Invaild Input! Data Extractor Is Not Found")


class InstaVendorDataExtractor:

    def extract_vendor_data(self,profile_info_elements,vendor_link,category="Vendor")->dict:

        if category is None:
            category="Vendor"

        vendor_data = {}

        if len(profile_info_elements) > 0:
            vendor_data["vendor_link"]=vendor_link
            vendor_data['vendor_name'] = profile_info_elements[0].get("content").split('•')[0]
            vendor_data['vendor_followers_num'] = profile_info_elements[1].get('content').split(", ")[0]
            vendor_data['vendor_following_num'] = profile_info_elements[1].get('content').split(", ")[1]


            try:
                vendor_phone_numbers = re.findall(r'\b\d{11,12}\b', profile_info_elements[2].get('content'))
                vendor_data['vendor_phone_numbers'] = ",".join(vendor_phone_numbers) if vendor_phone_numbers else "UNKNOWN"
            except Exception as e:
                vendor_data['vendor_phone_numbers'] = "UNKNOWN"

            try:
                vendor_locations = [location for location in egypt_locations_dict
                                    if location.lower() in profile_info_elements[2].get('content').lower()]
                
                vendor_data['vendor_locations'] = ",".join(vendor_locations) if vendor_locations else "UNKNOWN"

            except Exception as e:
                vendor_data['vendor_locations'] = "UNKNOWN"

            vendor_data['vendor_category'] = category

            description = profile_info_elements[2].get('content').lower()
            vendor_data['vendor_description'] = description if description and description != "" else "Not Available"
        
        return vendor_data
    
    def convert_vendors_list_to_df(self,vendors_info_list)->pd.DataFrame:
        df = pd.DataFrame(vendors_info_list)
        return df
    

    
    def extract_and_convert_vendor(self,scraper:wv_scraper.InstaVendorsScraper)->pd.DataFrame:
        
            collected_vendors_data=[]

            for index,link in enumerate(scraper.links_list):

                try:
                    page=scraper.drv.initialize_requests_client(link)
                except Exception as e:
                    raise Exception("Error In Connection")
                
                soup=BeautifulSoup(page.content,"lxml")
                profile_info_elements=soup.find_all(VsHelper().find_meta_with_description)

                #Check if category_list exists and has the same length of links_list
                if scraper.category_list and len(scraper.category_list)==len(scraper.links_list):
                    vendor_info=self.extract_vendor_data(profile_info_elements=profile_info_elements,
                                                         category=scraper.category_list[index],
                                                         vendor_link=link)
                else:
                    vendor_info=self.extract_vendor_data(profile_info_elements=profile_info_elements,
                                                         vendor_link=link)

                collected_vendors_data.append(vendor_info)

            vendor_info_df=self.convert_vendors_list_to_df(collected_vendors_data)

            #print(vendor_info_df)

            return vendor_info_df


class ArabiawVendorDataExtractor:


    def __init__(self):
        self.all_included_vendors_list=[]


    def extract_vendor_data_process(self,
                                    vendor_link,
                                    scraper:wv_scraper.ArabiawWebsiteVendorsScraper):
        
        vendor_data = {}

     

        try:
            
            drv=scraper.drv.initialize_requests_client(vendor_link)
            

            if (drv.status_code == 200): 
                
                page=drv.content.decode("utf-8")
                soup=BeautifulSoup(page,"lxml")
                json_tag= soup.find('script', id='__NEXT_DATA__')
                json_data = json.loads(json_tag.string)["props"]
                vendor_data["vendor_link"]=vendor_link

        except Exception as e:

            raise ConnectionError("The Driver Cannot Connect To The Website!")
        
        try:
            name=json_data["pageProps"]["pageData"]["meta"]["title"]
            vendor_data["vendor_name"]= name if name is not None else "-"

        except Exception as e:

            vendor_data["vendor_name"]="UNKNOWN"

        
        try:
            des=json_data["pageProps"]["pageData"]["meta"]["description"]
            vendor_data["vendor_description"]= des if des is not None else "-"

        except Exception as e:

            vendor_data["vendor_description"]="UNKNOWN"




        try:
            price=json_data["pageProps"]["pageData"]["price"]
            vendor_data["vendor_price"]= price if price is not None else "-"


        except Exception as e:

            vendor_data["vendor_price"]="UNKNOWN"

        try:
            category=json_data["pageProps"]["pageData"]["categories"][0]
            vendor_data['vendor_category']= category if category is not None else "-"
        
        except Exception as e:
            vendor_data["vendor_category"]="Vendor"


        try:
            address=json_data["pageProps"]["pageData"]["address"]
            vendor_data['vendor_locations']= address if address is not None else "-"

        except Exception as e:
            vendor_data['vendor_locations']="UNKNOWN"
        

        try:
            phone= json_data["pageProps"]["pageData"]["phone"]
            vendor_data['vendor_phone_numbers']= phone if phone is not None else "-"
        
        except Exception as e:
            vendor_data['vendor_phone_numbers']='UNKNOWN'

        self.all_included_vendors_list.append(vendor_data)
        

        serializer = serializers.VendorSerializer(data=vendor_data)
        serializer_all = serializers.AllVendorSerializer(data=vendor_data)

        if serializer.is_valid() and not models.VendorInfo.objects.filter(vendor_link=vendor_link).exists():
            serializer.save()

            if serializer_all.is_valid():
                serializer_all.save()

        drv.close()


    def extract_each_vendor_data(self,
                            collected_vendors_links,
                            scraper:wv_scraper.ArabiawWebsiteVendorsScraper)->list:
        
        
        thread_list=[]
        
        for vendor_link in collected_vendors_links:

            vendor_thread=Thread(target=self.extract_vendor_data_process,args=(vendor_link,scraper))
            vendor_thread.start()
            
            thread_list.append(vendor_thread)

        for t in thread_list:
            t.join()

            

        return self.all_included_vendors_list
    
 
    
    def convert_vendors_list_to_df(self,vendors_info_list)->pd.DataFrame:
        df = pd.DataFrame(vendors_info_list)
        return df
    
    def collect_vendors_links(self, scraper: wv_scraper.ArabiawWebsiteVendorsScraper) -> list:
        collected_vendors_links = []

        for link in scraper.links_list:
            try:
                page = scraper.drv.initialize_requests_client(link)
            except Exception as e:
                raise Exception("Error In Connection")
            
            if page.status_code == 200:
                soup = BeautifulSoup(page.content, "lxml")

                # Extract category name from the link
                category_name = link.split('/')[-1].split("?")[0]

                # Check if the category already exists in the database
                try:
                    vendor_category = models.VendorCategory.objects.get(vendor_category=category_name)
                    category_tid = vendor_category.vendor_category_tid  # If found, use existing tid

                except models.VendorCategory.DoesNotExist:
                    # If not found, get the category_id from the page
                    script_tag = soup.find('script', id='__NEXT_DATA__')
                    json_data = json.loads(script_tag.string)
                    category_tid = json_data['props']['pageProps']['pageData']["tid"]
                    
                    # Create a new entry in the VendorCategory model
                    vendor_category = models.VendorCategory.objects.create(vendor_category=category_name, vendor_category_tid=category_tid)

                page.close()

                link_page_num = int(link.split("?page=")[1]) - 1

                vendors_list_url = f"https://www.arabiaweddings.com/api/vendor/list?&page={link_page_num}&tid={category_tid}"

                json_v_data = scraper.drv.initialize_requests_client(vendors_list_url)

                # Construct a list of full URLs for each vendor within a category
                vendors_category_list = [
                    f"https://www.arabiaweddings.com/{x['url']}"
                    for x in json_v_data.json()["results"]
                ]

                collected_vendors_links.extend(vendors_category_list)

                json_v_data.close()

        return collected_vendors_links
        
    
    

    
    def extract_and_convert_vendor(self,
                                   scraper:wv_scraper.ArabiawWebsiteVendorsScraper)->pd.DataFrame:
        


        collected_vendors_links=self.collect_vendors_links(scraper)
        #print(len(collected_vendors_links))

        each_vendor_info=self.extract_each_vendor_data(collected_vendors_links,scraper)
        df=self.convert_vendors_list_to_df(each_vendor_info)

        # print(len(df))

        
        if not os.path.exists('./vendors_scraper/vendors_helper/csv_outputs'):
            os.makedirs('./vendors_scraper/vendors_helper/csv_outputs')
        df.to_csv("./vendors_scraper/vendors_helper/csv_outputs/output.csv",index=False)

        return df





         
            
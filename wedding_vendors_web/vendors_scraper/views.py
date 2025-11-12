from rest_framework.viewsets import ModelViewSet
from rest_framework.response import Response
from rest_framework.decorators import action
from rest_framework import status
import pandas as pd
from vendors_scraper.models import VendorInfo,AllVendorInfo,InstaVendorInfo
from . import serializers
from vendors_scraper.vendors_helper.wedding_vendors_scraper import WeddingVendorScraperFactory
from vendors_scraper.vendors_helper.vendor_scraper_helper import VendorScraperHelper

class AllVendorViewSet(ModelViewSet):
    queryset=AllVendorInfo.objects.all()
    serializer_class=serializers.AllVendorSerializer

    @action(detail=False,methods=["get"])
    def get_vendors_by_category(self,request,category):
        vendors=AllVendorInfo.objects.filter(vendor_category=category)
        if vendors.exists():
            # Serialize the vendor data
            serializer = self.get_serializer(vendors, many=True)
            return Response(serializer.data, status=status.HTTP_200_OK)
         
        # Check for similar categories (not identical)
        similar_vendors = AllVendorInfo.objects.filter(vendor_category__startswith=category)
        
        if similar_vendors.exists():
            # Serialize the similar vendor data
            serializer = self.get_serializer(similar_vendors, many=True)
            return Response({
                'message': 'No exact match found, but similar categories were found.',
                'similar_vendors': serializer.data
            }, status=status.HTTP_206_PARTIAL_CONTENT)  # 206 for partial content
        
        # Return a 404 response if no vendors are found
        return Response({'detail': 'No vendors found for this category.'}, status=status.HTTP_404_NOT_FOUND)


class VendorViewSet(ModelViewSet):
    queryset=VendorInfo.objects.all()
    serializer_class=serializers.VendorSerializer

    

    @action(detail=False, methods=['get'])
    def scrape_vendors(self, request):
        vendor_categories = request.query_params.getlist('category', [])

        vendor_links=VendorScraperHelper().categories_to_links(vendor_categories)

        #print(vendor_links)

        if not vendor_links:
            return Response([], status=status.HTTP_200_OK)  # Return an empty list if no categories are provided

        # Create the scraper
        scraper = WeddingVendorScraperFactory().create_scraper("Arabiaw", links_list=vendor_links)

        # Scrape vendor info
        scraped_data = scraper.scrape_vendors_info()

        if isinstance(scraped_data, pd.DataFrame):
            return Response(scraped_data.to_dict(orient="records"), status=status.HTTP_200_OK)

        return Response({'error': 'Failed to scrape vendor info.'}, status=status.HTTP_500_INTERNAL_SERVER_ERROR)






class VendorInstaViewSet(ModelViewSet):
    queryset=InstaVendorInfo.objects.all()
    serializer_class=serializers.VendorInstaSerializer


    def get_serializer_class(self):
        if self.action == 'scrape_insta_vendors':
            return serializers.ScrapeInstaVendorSerializer
        return super().get_serializer_class()

    @action(detail=False,methods=['POST', 'GET'])
    def scrape_insta_vendors(self,request):
        if request.method == 'GET':
   
            return Response([], status=status.HTTP_200_OK)

        elif request.method == 'POST':

            links_list = request.data.get('links_list', [])
            category_list = request.data.get('category_list', [])

            if not links_list:
                return Response({"error": "links_list is required."}, status=status.HTTP_400_BAD_REQUEST)

            try:
                scraper = WeddingVendorScraperFactory().create_scraper("Instagram", links_list=links_list, category_list=category_list)
                scraped_data = scraper.scrape_vendors_info()  # Assuming this method returns a DataFrame

                if isinstance(scraped_data, pd.DataFrame):
                    # Convert DataFrame to a list of dictionaries
                    scraped_data_list = scraped_data.to_dict(orient="records")
                

                    # Iterate through the list of dictionaries and cache data
                    for vendor_data in scraped_data_list:
                        vendor_link = vendor_data['vendor_link']  # Adjust based on your DataFrame structure

                        # Check if the vendor already exists
                        if not InstaVendorInfo.objects.filter(vendor_link=vendor_link).exists():
                            # Use the serializer to create a new VendorInfo instance
                            serializer = serializers.VendorInstaSerializer(data=vendor_data)
                            serializer_all = serializers.AllVendorSerializer(data=vendor_data)


                            if serializer.is_valid():
                                serializer.save()  # Save the new instance

                            if serializer_all.is_valid():
                                serializer_all.save() 
                                
                            
                            else:
                                return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

                    return Response({
                        "message": "Scraping and caching completed successfully.",
                        "saved_vendors": scraped_data_list
                    }, status=status.HTTP_200_OK)

                return Response({"error": "Scraping did not return a valid DataFrame."}, status=status.HTTP_400_BAD_REQUEST)

            except Exception as e:
                return Response({"error": str(e)}, status=status.HTTP_500_INTERNAL_SERVER_ERROR)
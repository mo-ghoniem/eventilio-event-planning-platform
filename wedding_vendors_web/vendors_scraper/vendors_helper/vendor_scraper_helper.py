import re

class VendorScraperHelper:
    
    # Custom function to find meta tag using regex
    def find_meta_with_description(self,tag):
        return tag.name == 'meta' and any(re.search(r'\b(description|og:title)\b',
                                                     value, 
                                                     re.IGNORECASE) for value in tag.attrs.values())
    

    def categories_to_links(self, categories):
        links = []
        
        for category in categories:
            # Use regex to search for a number in the category name
            match = re.search(r'\d+', category)
            num = int(match.group()) if match else 1  # Default to 1 if no number found
            
            # Remove the number from the category
            cleaned_category = re.sub(r'\d+', '', category).strip()
            
            links.append(f"https://www.arabiaweddings.com/cairo/{cleaned_category}?page={num}")

        return links

from django.contrib import admin
from vendors_scraper.models import VendorInfo,VendorCategory,AllVendorInfo,InstaVendorInfo

admin.site.register(AllVendorInfo)
admin.site.register(VendorInfo)
admin.site.register(InstaVendorInfo)
admin.site.register(VendorCategory)

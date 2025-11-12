from django.db import models

class BaseVendorInfo(models.Model):
    vendor_name = models.CharField(max_length=150, default="-")
    vendor_link = models.CharField(max_length=255, default="-")
    vendor_description = models.CharField(max_length=255, default="-")
    vendor_category = models.CharField(max_length=50, default="-")
    vendor_locations = models.CharField(max_length=255, default="-")
    vendor_price = models.CharField(max_length=15, default="-")
    vendor_phone_numbers = models.CharField(max_length=250, default="-")
    vendor_followers_num = models.CharField(max_length=20, default="-")
    vendor_following_num = models.CharField(max_length=20, default="-")

    class Meta:
        abstract = True 

class AllVendorInfo(BaseVendorInfo):
    class Meta:
        verbose_name = "All Vendor"
        verbose_name_plural = "All Vendors"

class VendorInfo(BaseVendorInfo):
    class Meta:
        verbose_name = "Vendor"
        verbose_name_plural = "Vendors"

class InstaVendorInfo(BaseVendorInfo):
    class Meta:
        verbose_name = "Instagram Vendor"
        verbose_name_plural = "Instagram Vendors"

class VendorCategory(models.Model):
    vendor_category= models.CharField(max_length=150, default="-")
    vendor_category_tid= models.CharField(max_length=50, default="-")
    class Meta:
        verbose_name = "Vendor Category"
        verbose_name_plural = "Vendor Categories"
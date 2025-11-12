# Django-WV-Vendor - Wedding Vendors App

## Overview

Django-WV-Vendor is a Django Rest Framework project designed to extract wedding vendors' information, such as addresses, phone numbers, and emails, from various social media platforms and websites. Initially focusing on Instagram, the scraper has expanded to other platforms, collecting data from vendors' websites in categories like DJs, catering services, venues, and more. This project aims to simplify the process of finding and communicating with wedding vendors by aggregating vendor information in one convenient location, saving users valuable time.

## Key Features

- Extracts wedding vendors' data from social media platforms and websites.
- Provides vendor information across various categories for easy access.
- Streamlines the vendor selection process for wedding planning.

## Technology and Enhancements

- **Design Patterns**: Utilizes the Factory design pattern for maintainability and scalability.
- **Concurrency**: Implements threads to enhance the scraping process for improved speed and efficiency.
- **Future Enhancements**: Integration with a larger microservices project to provide vendor data. Scalability to include scraping from additional websites.

## Technologies Used

- **Framework**: Django Rest Framework
- **Web Scraping**: Selenium, Requests, Beautiful Soup
- **Data Manipulation**: Pandas
- **Database**: SQLAlchemy, PostgreSQL

Django-WV-Vendor serves as a valuable tool for users seeking wedding vendors, offering a centralized platform for vendor information and simplifying the vendor selection process.

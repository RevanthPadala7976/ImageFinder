## Overview

Image Finder is a web application that allows users to crawl a provided URL and extract all images, including favicons and SVG icons, from the webpage. The results are displayed in a user-friendly interface and returned as a JSON response.

## Features
Web Crawling: Efficiently crawls the provided URL and sub-pages to extract image URLs.
Image Extraction: Extracts images from <img> tags, as well as favicons and SVG icons from <link> elements.
Multi-threading: Uses ExecutorService for concurrent crawling to improve performance.
Responsive UI: Modern, clean interface with real-time feedback and results display.
Thread-safe Data Handling: Ensures safe concurrent access to shared data structures.

## Technologies Used
Frontend: HTML, CSS, JavaScript
Backend: Java (Servlets)
Libraries: Jsoup for web scraping, Gson for JSON conversion
Server: Jetty for running the web application

## Prerequisites
Java: 11 or higher
Maven: 3.6.3 or higher
Jetty: 9.4.30.v20200611 or higher

You have one week to work on the submission from the time when you receive it. To submit you assignment, zip up your project (`imagefinder.zip`) and email it back to me. **Please include a list of URLs that you used to test in your submissions.** You should place them in the attached `test-links.txt` file found in the root of this project.

## Structure
The ImageFinder servlet is found in `src/main/java/com/eulerity/hackathon/imagefinder/ImageFinder.java`. This is the only provided Java class. Feel free to add more classes or packages as you see fit. 

The main landing page for this project can be found in `src/main/webapp/index.html`. This page contains more instructions and serves as the starting page for the web application. You may edit this page as much as it suits you, and/or add other pages. 

Finally, in the root directory of this project, you will find the `pom.xml`. This contains the project configuration details used by maven to build the project. If you want/need to use outside dependencies, you should add them to this file.

## Running the Project
Here we will detail how to setup and run this project so you may get started, as well as the requirements needed to do so.

### Requirements
Before beginning, make sure you have the following installed and ready to use
- Maven 3.5 or higher
- Java 8
  - Exact version, **NOT** Java 9+ - the build will fail with a newer version of Java

### Setup
To start, open a terminal window and navigate to wherever you unzipped to the root directory `imagefinder`. To build the project, run the command:

>`mvn package`

If all goes well you should see some lines that end with "BUILD SUCCESS". When you build your project, maven should build it in the `target` directory. To clear this, you may run the command:

>`mvn clean`

To run the project, use the following command to start the server:

>`mvn clean test package jetty:run`


## Submission
When you are finished working on the project, before zipping up and emailing back your submission, **PLEASE RUN ONE LAST `mvn clean` COMMAND TO REMOVE ANY UNNECESSARY FILES FROM YOUR SUBMISSION**. Please also make sure to add the URLs you used to test your project to the `test-links.txt` file. After doing these things, you may zip up the root directory (`imagefinder`) and email it back to us.

## Usage
1. Enter a URL into the input field.
2. Click the "Submit!" button.
3. The application will display a "Processing..." message.
4. The results will be shown in the textarea as a JSON array of image URLs and icon URLs.

## Acknowledgments
- Jsoup for HTML parsing and web scraping
- Gson for JSON conversion
- Jetty for running the web server

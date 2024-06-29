# ImageFinder

![Java](https://img.shields.io/badge/Java-11%2B-007396?style=for-the-badge&logo=java&logoColor=white)
![HTML](https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge&logo=html5&logoColor=white)
![CSS](https://img.shields.io/badge/CSS3-1572B6?style=for-the-badge&logo=css3&logoColor=white)
![Jsoup](https://img.shields.io/badge/Jsoup-1.14.3-brightgreen?style=for-the-badge)
![Maven](https://img.shields.io/badge/Maven-3.6.3%2B-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)
![Jetty](https://img.shields.io/badge/Jetty-9.4.30.v20200611-000000?style=for-the-badge&logo=eclipse&logoColor=white)
![MIT License](https://img.shields.io/badge/License-MIT-blue?style=for-the-badge)

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
- Java: 11 or higher
- Maven: 3.6.3 or higher
- Jetty: 9.4.30.v20200611 or higher

## Structure
1. The ImageFinder servlet is found in `src/main/java/com/eulerity/hackathon/imagefinder/ImageFinder.java`.
2. The main landing page for this project can be found in `src/main/webapp/index.html`. This page contains more instructions and serves as the starting page for the web application. 
3. Finally, in the root directory of this project, you will find the `pom.xml`. This contains the project configuration details used by maven to build the project.

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

Open application in your browser:

>`http://localhost:9090`

## Usage
1. Enter a URL into the input field.
2. Click the "Submit!" button.
3. The application will display a "Processing..." message.
4. The results will be shown in the textarea as a JSON array of image URLs and icon URLs.

## Acknowledgments
- Jsoup for HTML parsing and web scraping
- Gson for JSON conversion
- Jetty for running the web server

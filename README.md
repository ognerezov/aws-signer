# AWS CloudFront sign server
This project is a simple serve tool for creating CloudFront sign links

## Features

* Configuration via environment variables. Easy to use via kubernetes or docker compose
* Caching
* Access restriction via application secrete
* Link creation via REST-API

## Getting started

* Create public/private key pair and upload public to CloudFront following official gide https://docs.aws.amazon.com/AmazonCloudFront/latest/DeveloperGuide/private-content-trusted-signers.html#private-content-adding-trusted-signers 
* Create CloudFront distribution with restricted access
* You could build this Java Spring project or pull container from docker hub, *docker pull ognerezov/aws-signer*
* Provide configuration via environment. docker-compose.yml make an example configuration
* Run container or app in configured environment

## API

* Default port is 5190
* GET /distribution - returns distribution url provided via DISTRIBUTION environment variable. This method is public
* Use app secret to authorize sign requests. Add Authorization header to sign request. Value of the header must be equal to APP_SECRET environment variable
* GET /link/<key> will return a signed link. There are some symbol limitations. "/" is now allowed
* POST "/links" {
  "list": ["key1","key2"]
  } User this method to get a bunch of links or avoid symbol limitation 

## Cache configuration

* LINK_TTL - the link will live this time (milliseconds)
* TTL_SAFE_PERIOD - link will be hold in cache for time = LINK_TTL - TTL_SAFE_PERIOD (milliseconds)
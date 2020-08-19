# README #

This README would normally document whatever steps are necessary to get your application up and running.

### What is this repository for? ###

* Management of FinanceMobile assets
* 0.0.1-SNAPSHOT

### Requirements? ###

* Maven >=3.3
* Java 8
* Mysql


### Create DB User ###

* `GRANT ALL PRIVILEGES ON fmassets.* TO 'fmassets'@'%' IDENTIFIED BY 'ask4help';`

### Coding Style ###

* Every developer will create their own feature braches and merge changes only when done.
* Every written code must be accompanied by appropraite test suites.
* Feature Braches are only mergeable into `Staging` branch. 

### Create Pull Request / Merge Request
* Push your latest change to your remote branch.
* Go online onto the project page on Bitbucket. 
* On the project menu click [Pull Request](https://bitbucket.org/investmobile/fm-assets/pull-requests/).
* Click on `Create Pull Request` .
* Select your branch and click `Create Pull Request` button.

### Commands ###
* Run Maven tests

	`mvn clean test`
	

* Verify Code Coverage

	`mvn -B verify`
	
* Run Application on commandline with Maven

	`./mvnw spring-boot:run`
	
	
* Run from IntelliJ

	- Right click on the main class(`FmassetsApplication`).
	- And click on `Run `FmassetsApplication'`
	


### Who do I talk to? ###

* Samuel 
* Godfred
* Albert
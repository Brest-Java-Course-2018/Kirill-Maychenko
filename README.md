# - Kirill-Maychenko

[![Build Status](https://travis-ci.org/Brest-Java-Course-2018/Kirill-Maychenko.svg?branch=master)](https://travis-ci.org/Brest-Java-Course-2018/Kirill-Maychenko)
[![Coverage Status](https://coveralls.io/repos/github/Brest-Java-Course-2018/Kirill-Maychenko/badge.svg)](https://coveralls.io/github/Brest-Java-Course-2018/Kirill-Maychenko)

    1. Check  
           
           $java -version  
           
           $export JAVA_HOME = ...
           
           $mvn -version
           
    2. Build
    
       
       $mvn clean install
       
    3. Preparing repotrs
     
       $mvn site
     
       $mvn site:stage
     
       check: ``<project>/target/stage/index.html``
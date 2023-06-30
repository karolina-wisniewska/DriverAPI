<a id="readme-top"></a>
<!-- PROJECT SHIELDS -->
<!--
*** I'm using markdown "reference style" links for readability.
*** Reference links are enclosed in brackets [ ] instead of parentheses ( ).
*** See the bottom of this document for the declaration of the reference variables
*** for contributors-url, forks-url, etc. This is an optional, concise syntax you may use.
*** https://www.markdownguide.org/basic-syntax/#reference-style-links
-->

[![MIT License][license-shield]][license-url]
[![LinkedIn][linkedin-shield]][linkedin-url]
<div align="center">
<h3 align="center">Driver API</h3>
  <p align="center">
    REST API providing resources for drivers' self-improvement platform 
    <br />
    <a href="https://github.com/karolina-wisniewska/DriverAPI"><strong>Explore the docs »</strong></a>
    <br />
  </p>
</div>

<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#license">License</a></li>
  </ol>
</details>


<a name="about-the-project"></a>
<!-- ABOUT THE PROJECT -->
## About The Project

REST API providing resources for drivers' self-improvement platform with:
- advices on safe driving 
- trainings to check user's knowledge

Advices may contain media such as images and video files.

Main functionalities:
- CRUD operations enabling admin management on the components 
- GET user's to-do advices
- GET advices to discover
- GET advice of the week
- POST share advice
- POST like advice
- GET check training
- GET advices by tag
- GET tag by name

Full API documentation is provided by Swagger after installation.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<a id="built-with"></a>
### Built With
* Java 17
* MySQL
* Maven
* Spring Boot
* Spring Security
* Hibernate
* Lombok
* IntelliJ IDEA
* OAuth2
* JWT
* OpenApi 3
<p align="right">(<a href="#readme-top">back to top</a>)</p>

<a id="getting-started"></a>
<!-- GETTING STARTED -->
## Getting Started
<a id="prerequisities"></a>
### Prerequisites
Java 17

<a id="installation"></a>
### Installation

To launch the application, you need to clone the GitHub project where this demo is hosted, and then run the main class.

1. Create database with name 'driver' on localhost:3306 

2. To launch the application, you need to clone the GitHub project, open it with IDE and then run the main class:
   ```sh
   git clone https://github.com/karolina-wisniewska/DriverAPI.git
   ```
   
3. API documentation can be found here: http://localhost:8080/swagger-ui/index.html.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<a id="contributing"></a>
<!-- CONTRIBUTING -->
## Contributing

Contributions are what make the open source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

If you have a suggestion that would make this better, please fork the repo and create a pull request. You can also simply open an issue with the tag "enhancement".
Don't forget to give the project a star! Thanks again!

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<a id="license"></a>
<!-- LICENSE -->
## License

Distributed under the MIT License. See `LICENSE.txt` for more information.

<p align="right">(<a href="#readme-top">back to top</a>)</p>


<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[license-shield]: https://img.shields.io/github/license/othneildrew/Best-README-Template.svg?style=for-the-badge
[license-url]: https://github.com/othneildrew/Best-README-Template/blob/master/LICENSE.txt
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://linkedin.com/in/karolina-wi


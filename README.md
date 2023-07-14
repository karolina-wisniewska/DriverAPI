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
        <li><a href="#demo">Demo</a></li>
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

There are two roles for API users: USER and ADMIN.

1. USER can access functionalities listed below:
- get user's to-do advices (GET)
- get advices to discover (GET)
- get advice of the week (GET)
- share advice (POST)
- like advice (POST)
- check training (GET)
- find advices by tag (GET)
- find tag by name (GET)

2. ADMIN can use all the above methods, as well as perform CRUD operations enabling management on components (advices, trainings, questions, answers, tags, media).

Full API documentation is provided by Swagger after installation. Methods available only for ADMIN access are described in Swagger-ui as `Admin access only`. 

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
* OAuth2
* JWT
* OpenApi 3
* JUnit
* IntelliJ IDEA
<p align="right">(<a href="#readme-top">back to top</a>)</p>

<a id="getting-started"></a>
<!-- GETTING STARTED -->
## Getting Started

<a id="demo"></a>
### Demo
1. Check out App Demo on YouTube:
   <br />
   <a href="https://www.youtube.com/watch?v=slIStjY72S4"><strong>Watch App Demo »</strong></a>
   <br />

<a id="prerequisities"></a>
### Prerequisites
Docker

<a id="installation"></a>
### Installation

1. To launch the application, you need to clone the GitHub project:
   ```sh
   git clone https://github.com/karolina-wisniewska/DriverAPI.git
   ```

2. Change directory to project root directory:
   ```sh
   cd DriverAPI/
   ```

3. Run docker-compose up in detached mode:
   ```sh
   docker-compose up -d
   ```

4. The web application starts on port 8080 in the localhost by default. Open the URL http://localhost:8080/swagger-ui/index.html to browse API documentation and test API.

5. API is secured using JWT Authentication. To test the application, go to `auth-controller` in swagger-ui.
- In order to login as a USER:
    - create new user in `/api/auth/registration` method,
    - authenticate in `/api/auth/token` to get your token,
    - use the token to get authorization
- In order to login as an ADMIN:
    - authenticate in `/api/auth/token` using `userName: Admin` and `password: Admin` to get your token,
    - use the token to get authorization

6. To close the app, remove containers and volumes run:
   ```sh
   docker-compose down -v
   ```

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


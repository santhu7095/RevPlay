\# RevPlay 🎵



RevPlay is a Java console-based music streaming application built using JDBC and MySQL.  

The system allows \*\*users\*\* and \*\*artists\*\* to interact with a structured music library.  

Users can search songs, create playlists, and simulate music playback, while artists can upload songs, create albums, and manage their profiles.



---



\## 🚀 Features



\### 👤 User Features

\- User Registration \& Login

\- Secure password handling

\- Search and browse songs

\- Create and manage playlists

\- Add songs to playlists

\- Music playback simulation

\- View listening history



\### 🎤 Artist Features

\- Artist Registration \& Login

\- Upload songs

\- Create albums

\- Manage songs and albums

\- Update artist profile



---



\## 🛠 Technologies Used



\- \*\*Java\*\* (Core Java)

\- \*\*JDBC\*\*

\- \*\*MySQL\*\*

\- \*\*Git\*\*



---



\## 🏗 Architecture Diagram



The application follows a layered architecture:



\- Presentation Layer (Java Console)

\- Business Logic Layer

\- Data Access Layer (JDBC)

\- Database Layer (MySQL)



!\[Architecture Diagram](docs/Architecture-Diagram.jpeg)



---



\## 🗄 Database Design (ER Diagram)



This ER diagram represents the conceptual database structure of the RevPlay application  

and shows relationships between users, artists, albums, songs, playlists,  

playlist\_song (bridge table), and listening history.



!\[ER Diagram](docs/ER-Diagram.jpeg)



---



\## 🗂 Database Schema



The database includes the following main tables:



\- users

\- artists

\- albums

\- songs

\- playlists

\- playlist\_song

\- listening\_history



Each table is connected using \*\*Primary Keys\*\* and \*\*Foreign Keys\*\* to maintain data integrity and proper relationships.



!\[Schema Diagram](docs/schema-diagram.png)



---



\## ▶ How to Run the Project



1\. Clone the repository:

git clone <your-repository-link>



2\. Import the project into your IDE (Eclipse / IntelliJ).



3\. Configure the MySQL database connection in the JDBC configuration file.



4\. Execute the SQL script to create required tables.



5\. Run `Main.java` to start the application.



---



\## 📂 Project Structure



RevPlay/

│

├── src/ → Java source files

├── docs/ → ER, Schema, Architecture diagrams

├── README.md



---



\## 🚀 Future Enhancements



\- Develop GUI version of the application

\- Implement song recommendation system

\- Add REST API integration

\- Deploy with cloud-based database

\- Add role-based authentication and authorization



---



\## 📌 Project Type



Individual Project



---



\## 👨‍💻 Author



Santhosh Akkem  

Aspiring Backend \& Data Analytics Developer




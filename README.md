# Twitter PubSub Replica

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![SQLite](https://img.shields.io/badge/SQLite-003B57?style=for-the-badge&logo=sqlite&logoColor=white)

  
## Project Description

**Twitter PubSub Replica** is a simplified simulation of Twitter’s data storage and timeline system, utilizing a **publish-subscribe (Pub/Sub)** model for distributing messages to users. This Java-based implementation decouples tweet publication and timeline delivery by mimicking how Twitter fans out messages to subscribers.

The system uses **SQLite** for lightweight, embedded databases to simulate distributed storage nodes. It is ideal for understanding how timelines can be constructed via centralized, sharded, or topic-based distribution systems without relying on external message brokers like Kafka or RabbitMQ.

  
## Features

- **Publish/Subscribe architecture** for asynchronous fanout  
- **SQLite-powered distributed databases** (`db1.sqlite3`, `pubsub.sqlite3`, etc.)  
- **Per-user mailbox abstraction**  
- **Centralized timeline generation logic**  
- **Simulated decentralized Pub/Sub timeline construction**  
- **Support for multiple storage backends using lightweight files**  



## Technologies Used

- **Java** — Core backend implementation  
- **SQLite** — Embedded relational database for local Pub/Sub simulation  



## Getting Started

### Prerequisites

- Java Development Kit (JDK)
- Git
- No need for external database setup—uses local `.sqlite3` files


### Installation

1. **Clone the Repository**
   ```bash
   git clone https://github.com/darrellathaya/low-twitter-pubsub.git
   cd low-twitter-pubsub
   ```

2. *(Optional)* Download JDBC SQLite driver if needed (e.g., from [Xerial SQLite JDBC](https://github.com/xerial/sqlite-jdbc)) and add it to your classpath.


3. **Compile the Project**
   ```bash
   javac -cp ".:sqlite-jdbc.jar" *.java
   ```


## Usage Guide

### Publish a New Tweet
```bash
java -cp ".:sqlite-jdbc.jar" Post <username> "<message>"
```

### Follow a User
```bash
java -cp ".:sqlite-jdbc.jar" Follow <follower> <followee>
```

### View Timeline using Centralized Logic
```bash
java -cp ".:sqlite-jdbc.jar" Timeline <username>
```

### View Timeline via Pub/Sub Simulation
```bash
java -cp ".:sqlite-jdbc.jar" PubSubTimeline <username>
```



## Project Structure

```
twitter-pubsub-replica/
├── META-INF/                         # Configuration directory
├── org/sqlite/                      # SQLite driver package
│
├── centralized.sqlite3              # Centralized database instance
├── db1.sqlite3                       # Simulated database node 1
├── pubsub.sqlite3                    # Pub/Sub-specific storage
│
├── Follow.java                       # Follower relationship logic
├── Follow.class
│
├── Post.java                         # Command to post messages
├── Post.class
│
├── Mailbox.java                      # Handles mailbox-like abstraction for delivery
│
├── Timeline.java                     # Centralized timeline engine
├── Timeline.class
│
├── PubSubTimeline.java               # Pub/Sub-based timeline fanout
├── PubSubTimeline.class
│
├── sqlite-jdbc.properties            # SQLite connection configs
├── README.md                         # Project documentation
```

---

## License

This project is licensed under the MIT License.

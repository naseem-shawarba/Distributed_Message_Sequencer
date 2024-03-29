# Distributed Message Sequencer

The Message Sequencer is a decentralized messaging system in Java. The system enables message broadcasting among multiple threads without relying on synchronization through physical clocks. It addresses fundamental challenges encountered in distributed systems and offers practical solutions applicable in real-world scenarios.

## Overview

The project focuses on facilitating message broadcasting among multiple threads using a centralized message sequencer. Each thread manages its own message queue, which serves as an inbox for incoming messages. As messages arrive, they are categorized into two types:

- **External Messages**: Originating from clients, these messages carry a randomly generated integer payload. Each external message is directed to the inbox queue of a single thread. Upon receipt, the message content is disseminated to all other threads via the message sequencer.

- **Internal Messages**: Inter-thread communication occurs through internal messages, which may include additional metadata such as thread IDs or counters. These messages can be sent from a thread to the message sequencer's inbox or vice versa.

The message sequencer acts as a central broadcasting service, ensuring that every internal message is propagated to all threads, including the sender. This mechanism guarantees consistent message ordering across all threads.

## Key Features

- **Customizable Thread Configuration**: The number of threads capable of receiving external messages can be adjusted using command-line arguments.
- **Persistent Message History**: Each thread maintains a log of received internal messages, ensuring data integrity even after program termination.
- **Multi-threaded Architecture**: The message sequencer operates in a separate thread, enhancing system concurrency.
- **Client Simulation**: The system simulates client activity by generating a user-defined number of external messages distributed randomly among threads.

## Installation
- Clone the repository
- Compile the project using your preferred Java IDE or command-line tools.

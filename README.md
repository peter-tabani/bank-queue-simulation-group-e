# Bank Queue Simulation

## Group members

- Peter Tabani — 169913
- Jozef Gitahi — 169997
- Gerald Raila — 169387
- Ryan Njenga — 168946

## What the program does

The bank has **one teller** and serves customers in arrival order. The setup screen defaults to 100 customers, interarrival times uniformly sampled between 1.0 and 5.0 minutes, and service times uniformly sampled between 1.0 and 6.0 minutes. Times are continuous decimal values, displayed to two decimal places. The results screen shows the complete customer table and the queue statistics requested in the assignment.

## Run in PowerShell

```powershell
javac BankQueueSimulation.java
java BankQueueSimulation
```

If PowerShell still cannot find Java after installation, open a new terminal or add the installed JDK to the current session:

```powershell
$env:Path = "C:\Program Files\Eclipse Adoptium\jdk-21.0.12.101-hotspot\bin;$env:Path"
```

## How to explain the code

1. `showSetup` creates the input screen for customer count and the two random time ranges.
2. `runSimulation` checks the inputs, then generates each customer's interarrival and service time with `minimum + random value × (maximum − minimum)`.
3. Each new arrival time is the previous arrival plus the generated interarrival time. Service starts at the later of the customer's arrival and the previous customer's departure.
4. Waiting time is service start minus arrival. Departure is service start plus service time. The program totals these values and teller idle time.
5. `showResults` displays the customer table and calculates averages by dividing totals by the customer count. The waiting percentage is the number who waited divided by the customer count.

The simulation uses random values, so the exact numbers change on each run.

## Screenshots for submission

- [Setup screen](screenshots/setup.png)
- [Results screen with customer table](screenshots/results.png)
- [Statistics summary](screenshots/statistics.png)

These images show one completed run with the default inputs. A new run may produce different statistics.

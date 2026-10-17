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




# TDT4186 Exercise P3
By Sandor Zeestraten - zeestrat@stud.ntnu.no

## Results
See the file [RESULTS.md](RESULTS.md) for the ouput of the simulations.

## Observations

##### CPU
Doubling the maximum uninterrupted CPU time for a process reduced the number of process switches and processed I/O, however it it decreased performance overal.
Throughput and the fraction of CPU time spent processing decreased and the average time spent in the system and waiting increased.

Halfing the same value increased the number of process switches and lowered the processed I/O operations while the throughput took a small hit.
The average time spent in the system and waiting was however lowered.

##### Memory
Doubling the memory size lowered the number of processed I/O operations somewhat and also lowered the average time spent in system somewhat.
Average throughput was however also a bit lower.

Halfing the same value led to an even lower throughput and increased time the CPU spent waiting.

##### I/O
Doubling the average I/O operation time also reduced the throughput. The amount of process switches increased somewhat while the number of processed I/O operations were lowered.

Halfing the same value resulted in almost the same throughput while doubling the average time spent in the system and also increasing the fraction of time the CPU spent processing.

##### Process arrival
Doubling the average time between process arrivals naturally lowered the throughput of the system.
The CPU spent half the time waiting while the there was no waiting on memory.

Halfing the same value has almost the same throughput as the default values and the CPU spends somewhat more time processing.
However this all has an cost on the average time spent in the system per process which is more than doubled.


# Java-Socket-Exercises

1) the server can echo  by typing echo#whatevermessagehere
whatever isbehind the # will be echoed.

2) by doing time#

3) the project has a client...if the client types time#  her will get the time

4) the server makes a new thread for every client who connects.

5) by entering help#  commands for all the exercises can be found and executed
hint:  everything before the # is a command and everything behind it will be comanded on, typing uppercase#hello will print back HELLO

6) by typing msgall#messagehere   u can send messages to all online clients. to see whos online u can type whoison#
also private messages can be send by msgto#username@message.
u can set ur username with setname#name

the class ChatAll handles all the messages that are to be send to all user by using a blodkingqueue.

7) its one server who can do it all!  can be found on our droplet at 165.227.151.92 port 47

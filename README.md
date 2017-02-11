# NamBot

A shitty bot built for Discord in Java.  

## Commands
These are just ordered in a way I found made sense when I implemented them, no real thought went into it  
ALL THE COMMANDS ARE USED WITH THE PREFIX "::"  
###### Admin Commands
`cleanup (0-99 | "max")` -> removes 0-99 of the last messages in the current text chat  
`getinfo [@user]` -> Retrieves info about that user (roles, snowflake, avatar URL, join date, etc)  
`setloggerchannel` -> Sets the current text chat to a logger chat  
`removeloggerchannel`-> Removes the current logger channel from the server  

###### Image Commands
`pat @user` -> Pats that user (creates gif)  
`sorryaboutexisting [@user]` -> Makes the user say sorry about existing (creates an image)  
`ship [@user @user]` -> If two users are mentioned, ships those users (creates an image)  
`highfive @user` -> Highfives that user (creates gif)  

###### Reaction Commands
`becool` -> sends :sunglasses:  
`beangery`-> sends :rage:  
`laugh`-> sends :joy:  
`beanormie` -> sends a randomized string of 5-10 emojis from a certain set of emojis.  
`shut [@user+]` -> Mentions all the users (if any), and posts "SHUT"  
`lmao` -> Sends "LMAO"  
`feelsbadman [phrase]` -> Sends picture feelsbadman.png, with eventual "phrase" if specified  
`arrogant` -> Sends picture arrogant.png  
`dab` -> Sends gif dab.gif  
`say phrase` -> Repeats phrase  
`salute` -> Sends picture salute.jpg  
`reee` -> Sends "REEE" with enough "E" to fill up the max length of a message  
`/wrist` -> Sends :knife: :hand_splayed: :sweat_drops:  

###### User Commands
`whoami [@user]` -> Gets the name and role of that user  
`ratecoolness [@user]` -> Rates the coolness of the user on a 0-10 scale  
`vote phrase` -> Puts the phrase up for vote (by thumbs up/down reactions)  
`meme [search term]` -> Generates a random meme if no search term is specified, tries to find the searched for meme otherwise  

###### Mini Game Commands
`fight @user` -> Starts a fight between the user who types the command and the mentioned user. Fight takes place in PMs.  
`tictactoe @user` -> Plays a round of tic tac toe with the user. Game takes place in PMs.  

## Library/Dependencies
Uses the library JDA (https://github.com/DV8FromTheWorld/JDA)  
Currently built for the version: 3.0 BETA2 108  

Also uses GifSequenceWriter.java, courtesy of Elliot Kroo  
http://elliot.kroo.net/software/java/GifSequenceWriter/

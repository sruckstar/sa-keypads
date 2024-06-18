# Keypads (GTA San Andreas)
This script restores the keypad code-entry system that was intended by the developers in the early stages of GTA San Andreas.

Keypads are only used in a few missions, and in all cases a single shot is enough to open a locked door. The script changes the algorithm of such keypads: they can no longer be destroyed by a shot, and to open them you need to enter a four-digit password. To do this, you have to choose combinations of numbers until they turn green. The code is randomly generated and will be different each time.

# For mission developers

You can use this script in your missions. All you have to do is create an object with the ID 2886. The script itself will find the created object and enable the code entry function. Note that the HAS_OBJECT_BEEN_DAMAGED condition (opcode 0366) must still be used to terminate the keyboard. However, the player will not be able to damage the keyboard, the script will do this after the code has been entered correctly.

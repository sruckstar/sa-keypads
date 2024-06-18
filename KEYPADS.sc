SCRIPT_START
{

LVAR_INT scplayer 
LVAR_INT panel number current_num num1 num2 num3 num4 ideal_num1 ideal_num2 ideal_num3 ideal_num4 
LVAR_INT mission_panel fake_panel_1 fake_panel_2 fake_panel_3 fake_panel_4 on_panel_flag
LVAR_FLOAT temp_float_x temp_float_y temp_float_z temp_float_heading

GET_PLAYER_CHAR 0 scplayer
panel = -1
number = 0

main_loop:
WAIT 0
IF IS_PLAYER_PLAYING 0
    IF on_panel_flag = 0
        GET_CHAR_COORDINATES scplayer temp_float_x temp_float_y temp_float_z
        IF GET_RANDOM_OBJECT_IN_SPHERE_NO_SAVE_RECURSIVE temp_float_x temp_float_y temp_float_z 10.0 1 mission_panel
            IF DOES_OBJECT_HAVE_THIS_MODEL mission_panel 2886
            AND NOT mission_panel = fake_panel_1
            AND NOT mission_panel = fake_panel_2
            AND NOT mission_panel = fake_panel_3
            AND NOT mission_panel = fake_panel_4
                SET_OBJECT_COLLISION mission_panel FALSE
                GET_OBJECT_COORDINATES mission_panel temp_float_x temp_float_y temp_float_z
                IF LOCATE_CHAR_ON_FOOT_3D scplayer temp_float_x temp_float_y temp_float_z 5.0 5.0 5.0 FALSE
                    PRINT_HELP_FOREVER KEYTTT
                    on_panel_flag = 1
                ENDIF
            ENDIF
        ENDIF
    ENDIF

    IF on_panel_flag = 1
        IF LOCATE_CHAR_ON_FOOT_3D scplayer temp_float_x temp_float_y temp_float_z 5.0 5.0 5.0 FALSE
        AND IS_BUTTON_PRESSED 0 4
            CREATE_MENU KEYSTT 29.0 170.0 10.0 4 0 1 1 panel 
            SET_PLAYER_CONTROL 0 FALSE
            GOSUB switch_number
            current_num = 1
            GENERATE_RANDOM_INT_IN_RANGE 0 9 ideal_num1
            GENERATE_RANDOM_INT_IN_RANGE 0 9 ideal_num2
            GENERATE_RANDOM_INT_IN_RANGE 0 9 ideal_num3
            GENERATE_RANDOM_INT_IN_RANGE 0 9 ideal_num4
            num1 = -1
            num2 = -1
            num3 = -1
            num4 = -1
            CLEAR_HELP
            PRINT_FORMATTED_NOW "Your code:" 9999999
            GOTO new_loop
        ELSE
            IF NOT LOCATE_CHAR_ON_FOOT_3D scplayer temp_float_x temp_float_y temp_float_z 5.0 5.0 5.0 FALSE
                on_panel_flag = 0
                CLEAR_HELP
            ENDIF
        ENDIF
    ENDIF
ENDIF
GOTO main_loop

new_loop:
WAIT 0
IF NOT panel = -1
    IF IS_BUTTON_PRESSED 0 7

        GOSUB set_plus
        GOSUB switch_number  

        WHILE IS_BUTTON_PRESSED 0 7
            WAIT 0
        ENDWHILE

    ELSE
        IF IS_BUTTON_PRESSED 0 5

            GOSUB set_minus
            GOSUB switch_number

            WHILE IS_BUTTON_PRESSED 0 5
            WAIT 0
        ENDWHILE

        ELSE
            IF IS_BUTTON_PRESSED 0 16
                SWITCH current_num
                    CASE 1
                        num1 = number
                        IF num1 = ideal_num1
                            GOSUB show_pin
                            current_num++
                        ELSE
                            GOSUB show_pin
                        ENDIF
                    BREAK
                    CASE 2
                        num2 = number
                        IF num2 = ideal_num2
                            GOSUB show_pin
                            current_num++
                        ELSE
                            GOSUB show_pin
                        ENDIF
                    BREAK
                    CASE 3
                        num3 = number
                        IF num3 = ideal_num3
                            GOSUB show_pin
                            current_num++
                        ELSE
                            GOSUB show_pin
                        ENDIF
                    BREAK
                    CASE 4
                        num4 = number
                        IF num4 = ideal_num4
                            GOSUB show_pin
                            current_num++
                        ELSE
                            GOSUB show_pin
                        ENDIF
                    BREAK
                ENDSWITCH
                
                WHILE IS_BUTTON_PRESSED 0 16
                     WAIT 0
                ENDWHILE

            ENDIF
        ENDIF
    ENDIF

    IF num1 = ideal_num1
    AND num2 = ideal_num2
    AND num3 = ideal_num3
    AND num4 = ideal_num4
        WAIT 1000
        DELETE_MENU panel
        GET_OBJECT_COORDINATES mission_panel temp_float_x temp_float_y temp_float_z
        GET_OBJECT_HEADING mission_panel temp_float_heading

        IF NOT DOES_OBJECT_EXIST fake_panel_1
            CREATE_OBJECT_NO_OFFSET 2886 temp_float_x temp_float_y temp_float_z fake_panel_1
            SET_OBJECT_HEADING fake_panel_1 temp_float_heading
        ELSE
            IF NOT DOES_OBJECT_EXIST fake_panel_2
                CREATE_OBJECT_NO_OFFSET 2886 temp_float_x temp_float_y temp_float_z fake_panel_2
                SET_OBJECT_HEADING fake_panel_2 temp_float_heading
            ELSE
                IF NOT DOES_OBJECT_EXIST fake_panel_3
                    CREATE_OBJECT_NO_OFFSET 2886 temp_float_x temp_float_y temp_float_z fake_panel_3
                    SET_OBJECT_HEADING fake_panel_1 temp_float_heading
                ELSE
                    CREATE_OBJECT_NO_OFFSET 2886 temp_float_x temp_float_y temp_float_z fake_panel_4
                    SET_OBJECT_HEADING fake_panel_4 temp_float_heading
                ENDIF
            ENDIF
        ENDIF

        SET_OBJECT_COLLISION mission_panel TRUE
        SET_OBJECT_COORDINATES mission_panel 0.0 0.0 0.0
        ADD_EXPLOSION_VARIABLE_SHAKE 0.0 0.0 0.0 8 0.0
        SET_PLAYER_CONTROL 0 TRUE
        GOTO main_loop
    ENDIF
ENDIF
goto new_loop

show_pin:
SWITCH current_num
    CASE 1
        IF num1 = ideal_num1
            PRINT_FORMATTED_NOW "Your code: ~g~%d" 9999999 num1
        ELSE
            PRINT_FORMATTED_NOW "Your code: ~r~%d" 9999999 num1
        ENDIF
    BREAK
    CASE 2
        IF num2 = ideal_num2
            PRINT_FORMATTED_NOW "Your code: ~g~%d ~g~%d" 9999999 num1 num2
        ELSE
            IF NOT num2 = -1
                PRINT_FORMATTED_NOW "Your code: ~g~%d ~r~%d" 9999999 num1 num2
            ENDIF
        ENDIF
    BREAK
    CASE 3
        IF num3 = ideal_num3
            PRINT_FORMATTED_NOW "Your code: ~g~%d ~g~%d ~g~%d" 9999999 num1 num2 num3
        ELSE
            IF NOT num3 = -1
                PRINT_FORMATTED_NOW "Your code: ~g~%d ~g~%d ~r~%d" 9999999 num1 num2 num3
            ENDIF
        ENDIF
    BREAK
    CASE 4
        IF num4 = ideal_num4
            PRINT_FORMATTED_NOW "Code confirmed: ~g~%d ~g~%d ~g~%d ~g~%d" 1000 num1 num2 num3 num4
        ELSE
            IF NOT num4 = -1
                PRINT_FORMATTED_NOW "Your code: ~g~%d ~g~%d ~g~%d ~r~%d" 9999999 num1 num2 num3 num4
            ENDIF
        ENDIF
    BREAK
ENDSWITCH
RETURN

set_plus:
IF number < 9
    number++
ELSE
    number = 0
 ENDIF
RETURN

set_minus:
IF number > 0
    number--
ELSE
    number = 9
ENDIF
RETURN

switch_number:
SWITCH number
    CASE 0
        SET_MENU_COLUMN panel 0 DUMMY KEYST1 KEYST4 KEYST7 KEYSTP DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY 
        SET_MENU_COLUMN panel 1 DUMMY KEYST2 KEYST5 KEYST8 KEYSG0 DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY
        SET_MENU_COLUMN panel 2 DUMMY KEYST3 KEYST6 KEYST9 KEYSTP DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY
    BREAK
    CASE 1
        SET_MENU_COLUMN panel 0 DUMMY KEYSG1 KEYST4 KEYST7 KEYSTP DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY 
        SET_MENU_COLUMN panel 1 DUMMY KEYST2 KEYST5 KEYST8 KEYST0 DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY
        SET_MENU_COLUMN panel 2 DUMMY KEYST3 KEYST6 KEYST9 KEYSTP DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY
    BREAK
    CASE 2
        SET_MENU_COLUMN panel 0 DUMMY KEYST1 KEYST4 KEYST7 KEYSTP DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY 
        SET_MENU_COLUMN panel 1 DUMMY KEYSG2 KEYST5 KEYST8 KEYST0 DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY
        SET_MENU_COLUMN panel 2 DUMMY KEYST3 KEYST6 KEYST9 KEYSTP DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY
    BREAK
    CASE 3
        SET_MENU_COLUMN panel 0 DUMMY KEYST1 KEYST4 KEYST7 KEYSTP DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY 
        SET_MENU_COLUMN panel 1 DUMMY KEYST2 KEYST5 KEYST8 KEYST0 DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY
        SET_MENU_COLUMN panel 2 DUMMY KEYSG3 KEYST6 KEYST9 KEYSTP DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY
    BREAK
    CASE 4
        SET_MENU_COLUMN panel 0 DUMMY KEYST1 KEYSG4 KEYST7 KEYSTP DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY 
        SET_MENU_COLUMN panel 1 DUMMY KEYST2 KEYST5 KEYST8 KEYST0 DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY
        SET_MENU_COLUMN panel 2 DUMMY KEYST3 KEYST6 KEYST9 KEYSTP DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY
    BREAK
    CASE 5
        SET_MENU_COLUMN panel 0 DUMMY KEYST1 KEYST4 KEYST7 KEYSTP DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY 
        SET_MENU_COLUMN panel 1 DUMMY KEYST2 KEYSG5 KEYST8 KEYST0 DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY
        SET_MENU_COLUMN panel 2 DUMMY KEYST3 KEYST6 KEYST9 KEYSTP DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY
    BREAK
    CASE 6
        SET_MENU_COLUMN panel 0 DUMMY KEYST1 KEYST4 KEYST7 KEYSTP DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY 
        SET_MENU_COLUMN panel 1 DUMMY KEYST2 KEYST5 KEYST8 KEYST0 DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY
        SET_MENU_COLUMN panel 2 DUMMY KEYST3 KEYSG6 KEYST9 KEYSTP DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY
    BREAK
    CASE 7
        SET_MENU_COLUMN panel 0 DUMMY KEYST1 KEYST4 KEYSG7 KEYSTP DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY 
        SET_MENU_COLUMN panel 1 DUMMY KEYST2 KEYST5 KEYST8 KEYST0 DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY
        SET_MENU_COLUMN panel 2 DUMMY KEYST3 KEYST6 KEYST9 KEYSTP DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY
    BREAK
    CASE 8
        SET_MENU_COLUMN panel 0 DUMMY KEYST1 KEYST4 KEYST7 KEYSTP DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY 
        SET_MENU_COLUMN panel 1 DUMMY KEYST2 KEYST5 KEYSG8 KEYST0 DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY
        SET_MENU_COLUMN panel 2 DUMMY KEYST3 KEYST6 KEYST9 KEYSTP DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY
    BREAK
    CASE 9
        SET_MENU_COLUMN panel 0 DUMMY KEYST1 KEYST4 KEYST7 KEYSTP DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY 
        SET_MENU_COLUMN panel 1 DUMMY KEYST2 KEYST5 KEYST8 KEYST0 DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY
        SET_MENU_COLUMN panel 2 DUMMY KEYST3 KEYST6 KEYSG9 KEYSTP DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY
    BREAK
ENDSWITCH   
RETURN

}
SCRIPT_END
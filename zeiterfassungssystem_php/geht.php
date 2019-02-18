<?php
/**
 * Created by IntelliJ IDEA.
 * User: abogdan
 * Date: 01.02.2019
 * Time: 08:46
 */
?>
<!doctype html>
<html>
<head>
    <link href="https://fonts.googleapis.com/css?family=Raleway" rel="stylesheet">

    <meta http-equiv="expires" content="0" />
    <meta http-equiv="CACHE-CONTROL" content="NO-CACHE" />
    <meta http-equiv="PRAGMA" content="NO-CACHE" />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width,initial-scale=1" />
    <meta name="description" content="" />
    <meta name="keywords" content="" />
    <meta name="author" content="" />
    <meta name="page-topic" content="Business" />
    <meta name="revisit-after" content="7 days" />
    <meta name="city" content="" />
    <meta name="state" content="" />
    <meta name="zipcode" content="" />
    <meta name="geo.placename" content="" />
    <meta name="geo.position" content="" />
    <meta name="geo.region" content="" />
    <meta name="ICBM" content="" />

    <link rel="shortcut icon" href="icon.ico" type="image/x-icon">
    <link rel="stylesheet" type="text/css" href="standard.css">
</head>
<body>

<!-- top-wrapper -->
<div id="top-wrapper">
    <a class="a2" href="index.php" title="Startseite"><img src="logo.png" height="90%" width="auto" alt="logo CC"></a>
</div>
<!-- Ende top-wrapper -->


<!-- site-wrapper -->
<div id="site-wrapper">


    <!-- content-center -->
    <div id="content-center">

        <?php
        //Beim ersten Aufruf formular aunzeigen
        if ((!isset($_POST["benutzer"]) || empty($_POST["benutzer"])) || ((!isset($_POST["passwort"])) || empty($_POST["passwort"])))
        {
            echo '         
            <form action="geht.php" method="post">
            <label for="benutzer">Benutzername:</label>
            <input type="text" id="benutzer" name="benutzer">
            <label for="passwort">Passwort:</label>
            <input type="password" id="passwort" name="passwort">
            <br /><br />
            <input type="submit" value="Login">
            </form>';
        }
        else {
            $user = $_POST["benutzer"];
            $pswEingabe = $_POST["passwort"];

            $servername = "localhost";
            $username = "root";
            $password = "";

            // Erstelle Verbindung
            $conn = mysqli_connect($servername, $username, $password);
            mysqli_select_db($conn, "db_zeiterfassung");
            // Check Verbindung
            if (!$conn) {
                die("Connection failed: " . mysqli_connect_error());
            } else {

                $sql = "SELECT id,vorname,nachname,passwort FROM mitarbeiter WHERE benutzername = '" . $user . "'";

                $result = mysqli_query($conn, $sql);
                $result = mysqli_fetch_array($result);
                $id = $result['id'];
                $vName = $result['vorname'];
                $nName = $result['nachname'];
                $pas = $result['passwort'];
                $msg_1 = "";
                $msg_2 = "";
                $heute = date("Y-m-d");

                //Benutzername und Passwort sind Ok
                if ($pswEingabe == $pas)
                {
                    //Nachschauen ob es fuer heute schon einen geht Eintrag gibt
                    $sql3 = "SELECT geht FROM arbeitszeiten WHERE userID = '" . $id . "' AND tag = '" . $heute . "' ORDER BY tag DESC LIMIT 1";
                    $result = mysqli_query($conn, $sql3);
                    $result3 = mysqli_fetch_array($result);
                    if (!empty($result3['geht']))
                    {
                        $msg_1 = "<h1>Du hast dich heute schon ausgeloggt!</h1>
                        <br />
                        <a class='button_red' href='index.php'>OK</a>";
                    }
                    else
                    {
                        //Nachschauen, ob es heute einen Eintrag fuer 'kommt' gibt
                        $sql2 = "SELECT kommt FROM arbeitszeiten WHERE userID = '" . $id . "' AND tag = '" . $heute . "' ORDER BY tag DESC LIMIT 1";
                        $result = mysqli_query($conn, $sql2);
                        $result2 = mysqli_fetch_array($result);

                        //Zeit nehmen
                        $uhrzeit = date("H:i");

                        if (empty($result2['kommt']))
                        {
                            $msg_2 = "<h1>Du hast dich heute nicht ein geloggt!</h1>
                                <h2>Bitte melde dich im Büro</h2>
                                <br />
                                Du hast dich um " . $uhrzeit . " ausgeloggt.
                                <br />
                                <h2>Schönen Feierabend!</h2>
                                <br /><br />
                                <a class='button_green' href='index.php'>OK</a>";

                            //'geht' - Zeit in db eintragen (insert)
                            $sql4 = "INSERT INTO arbeitszeiten (userID, tag, geht) VALUES('" . $id . "','" . $heute . "','" . $uhrzeit . "')";
                            @mysqli_query($conn, $sql4);
                        }
                        else
                        {
                            $msg_2 = "<h1>Hallo " . $vName . " " . $nName . "</h1> 
                               <br />
                                Du hast dich um " . $uhrzeit . " ausgeloggt.
                                <br />
                                <h2>Schönen Feierabend!</h2>
                                <br /><br />
                                <a class='button_green' href='index.php'>OK</a>";

                            //'geht' - Zeit in db eintragen (update)
                            $sql2 = "UPDATE arbeitszeiten SET geht = '" . $uhrzeit . "' WHERE userID = '" . $id . "' AND tag = '" . $heute . "' ";
                            @mysqli_query($conn, $sql2);
                        }
                        //Benutzername oder Passwort ist falsch
                    }
                }
                else
                {
                    //Meldung ausgeben
                    $msg_2 = "<h1>Benutzername oder Passwort ist falsch!</h1>
                    <br />
                    <form action=\"geht.php\" method=\"post\">
                    <label for=\"benutzer\">Benutzername:</label>
                    <input type=\"text\" id=\"benutzer\" name=\"benutzer\">
                    <label for=\"passwort\">Passwort:</label>
                    <input type=\"password\" id=\"passwort\" name=\"passwort\">
                    <br /><br />
                    <input type=\"submit\" value=\"Login\">
                    </form>
                    ";
                }
            }

            // Verbindung schliessen
            mysqli_close($conn);
            echo "<p>" . $msg_1 . "<br />" . $msg_2 . "</p>";
        }
        ?>

    </div>
    <!-- Ende content-center -->


    <!--
    <div style="clear:both"></div>
    -->
</div>
<!-- Ende site-wrapper -->
<!-- footer -->
<div id="footer">

</div>
<!-- Ende footer -->

</body>
</html>

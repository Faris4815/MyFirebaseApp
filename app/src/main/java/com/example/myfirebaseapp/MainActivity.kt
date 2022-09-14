package com.example.myfirebaseapp

import android.content.Context
import android.content.Intent
import android.icu.number.IntegerWidth
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    var emailEditText : EditText? = null
    var passwordEditText : EditText? = null
    lateinit var register_BTN : Button
    lateinit var login_BTN : Button

    private lateinit var auth: FirebaseAuth

    val database = Firebase.database("https://absolute-theme-351121-default-rtdb.europe-west1.firebasedatabase.app/")
    val databaseMessageRef = database.getReference("message")



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        emailEditText = findViewById(R.id.editTextEmailAddress)
        passwordEditText = findViewById(R.id.editTextPassword)
        register_BTN = findViewById(R.id.register_BTN)
        login_BTN = findViewById(R.id.login_BTN)

        auth = Firebase.auth

        register_BTN.setOnClickListener{
            Log.i("Myinfo", "ich wurde ausgegeben")
            onCLickRegister()
        }

        login_BTN.setOnClickListener{
            onClickLogin()
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            //TODO: in reload() soll die UI geupdated werden, falls der Nutzer noch angemledet ist. Er soll also nicht zum Anmeldebildschirm kommen, sondern zu seiner Seite/Profil weitergeleiet werden.
            //reload();
        }
        loadLoginData()
    }

    fun onCLickRegister() {
        var email = emailEditText?.text.toString()
        var password = passwordEditText?.text.toString()


        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the registered user's information
                    Toast.makeText(
                        baseContext, "Authentication success.",
                        Toast.LENGTH_SHORT
                    ).show()
                    //TODO: Wenn man sich erfolgreich registriert hat, soll man sofort auf sein Profil kommen
                    //val user = auth.currentUser
                    //updateUI(user)
                    database.reference.child("users").child(task.result.user?.uid.toString()).child("email").setValue(emailEditText?.text.toString())
                    login()
                } else {
                    // If registration fails, display a message to the user.
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    //updateUI(null)
                }
            }
    }

    fun onClickLogin() {
        var email = emailEditText?.text.toString()
        var password = passwordEditText?.text.toString()

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    //updateUI(user)
                    Toast.makeText(
                        baseContext, "Login success.",
                        Toast.LENGTH_SHORT
                    ).show()
                    login()

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    //updateUI(null)
                    Toast.makeText(
                        baseContext, "Login failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    //Die methode soll den Nutzer, wenn denn einer eineloggt ist, zu der nächsten Activity bringen, wo man dann snaps senden kann. NOTE: Im Hintergrund ist ein Nutzer angemeldet und wir wechseln einfach zu einer anderen Activity.
    fun login() {
        val intent = Intent(this, SnapsActivity::class.java)
        startActivity(intent)
        saveLoginData()
    }

    //Speichert die zuletzt eineggebenen Daten damit man sie nicht immer neu eintragen muss. Die Funktion loadLoginData() lädt sie dann in den EditText rein
    fun saveLoginData(){
        val sharedPreference =  getSharedPreferences("LoginInfo", Context.MODE_PRIVATE)
        var editor = sharedPreference.edit()
        editor.putString("userEmail", emailEditText?.text.toString())
        editor.putString("userPassword", passwordEditText?.text.toString())
        editor.commit()
    }


    fun loadLoginData(){
        val sharedPreference =  getSharedPreferences("LoginInfo", Context.MODE_PRIVATE)
        emailEditText?.setText(sharedPreference.getString("userEmail", null))
        passwordEditText?.setText(sharedPreference.getString("userPassword", null))
    }

}
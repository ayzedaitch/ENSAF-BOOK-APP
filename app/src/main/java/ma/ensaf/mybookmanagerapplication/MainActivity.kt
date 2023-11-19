package ma.ensaf.mybookmanagerapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ma.ensaf.mybookmanagerapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    //view binding

    private lateinit var binding:ActivityMainBinding
    //lateinit keyword is used to declare a non-null variable that will be initialized at a later point in the code

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding. inflate(layoutInflater)
        setContentView(binding.root)

        //handle click, Login
        binding.loginBtn.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        //hand click, SignUp
        binding.signBtn.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

    }
}
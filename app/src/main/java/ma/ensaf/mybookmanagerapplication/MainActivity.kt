package ma.ensaf.mybookmanagerapplication

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
            //will do later
        }

        //hand click, skip and continue to main screen
        binding.skipBtn.setOnClickListener {
            //will do later
        }

    }
}
package ma.ensaf.mybookmanagerapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ma.ensaf.mybookmanagerapplication.databinding.ActivityPdfListAdminBinding

class PdfListAdminActivity : AppCompatActivity() {

    //view binding
    private lateinit var binding: ActivityPdfListAdminBinding

    //category id, title
    private var categoryId = ""
    private var category = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPdfListAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //get from intent, that we passed from adapter
        val intent = intent
        categoryId = intent.getStringExtra("categoryId")!!
        category = intent.getStringExtra("category")!!

        //set pdf category
        binding.subTitleTv.text = category
        //Load pdf/books
        loadPdfList()
    }

    private fun loadPdfList() {

    }
}
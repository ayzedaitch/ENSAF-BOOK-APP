package ma.ensaf.mybookmanagerapplication

import android.app.AlertDialog
import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import ma.ensaf.mybookmanagerapplication.databinding.ActivityPdfEditBinding

class PdfEditActivity : AppCompatActivity() {

    //view binding
    private lateinit var binding: ActivityPdfEditBinding

    private companion object{
        private const val TAG = "PDF_EDIT_TAG"
    }


    //book id get from intent started from Adapter Pdf Admin
    private var bookId=""

    //progress dialog
    private lateinit var progressDialog: ProgressDialog

    //arraylist to hold category titles
    private lateinit var categoryTitleArrayList: ArrayList<String>

    //arraylist to hold category ids
    private lateinit var categoryIdArrayList: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPdfEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //get book id to edit the book info
        bookId= intent.getStringExtra("bookId")!!

        //setup progress dialog
        progressDialog = ProgressDialog( this)
        progressDialog.setTitle("Please wait")
        progressDialog.setCanceledOnTouchOutside(false)

        loadCategories()

        //handle click, goback
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

        //handle click, pick category
        binding.categoryTv.setOnClickListener {
            categoryDialog()
        }

        //handle click, begin update
        binding.submitBtn.setOnClickListener {

        }
    }

    private var selectedCategoryId = ""
    private var selectedCategoryTitle = ""
    private fun categoryDialog() {
        /*Show dialog to pick the category of pdf/book. we already got the categories*/

        //make string array from arraylost of string
        val categoriesArray = arrayOfNulls<String>(categoryTitleArrayList.size)
        for (i in categoryTitleArrayList.indices) {
            categoriesArray[i]= categoryTitleArrayList[i]
        }

        //alert dialog
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Choose Category")
            .setItems(categoriesArray){dialog, position ->
                //handle click, save clicked category id and title
                selectedCategoryId= categoryIdArrayList[position]
                selectedCategoryTitle = categoryTitleArrayList[position]

                //set to textview
                binding.categoryTv.text = selectedCategoryTitle

            }
            .show() // show dialog
    }

    private fun loadCategories() {
        Log.d(TAG,"LoadCategories: loading categories...")

        categoryTitleArrayList = ArrayList()
        categoryIdArrayList = ArrayList()
        val ref = FirebaseDatabase.getInstance().getReference("Categories")
        ref.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                //clear list before starting adding data into them
                categoryIdArrayList.clear()
                categoryTitleArrayList.clear()

                for (ds in snapshot.children) {

                    val id="${ds.child("id").value}"
                    val category="${ds.child("category").value}"

                    categoryIdArrayList.add(id)
                    categoryTitleArrayList.add(category)

                    Log.d(TAG,"onDataChange: Category ID $id")
                    Log.d(TAG,"onDataChange: Category $category")
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}
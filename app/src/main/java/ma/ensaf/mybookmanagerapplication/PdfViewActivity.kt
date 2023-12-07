package ma.ensaf.mybookmanagerapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import ma.ensaf.mybookmanagerapplication.databinding.ActivityPdfViewBinding

class PdfViewActivity : AppCompatActivity() {
    //View binding
    private lateinit var binding: ActivityPdfViewBinding

    //Tag
    private companion object{
        const val TAG = "PDF_VIEW_TAG"
    }

    //book id
    var bookId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPdfViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //get book id from intent, it will be used to load book from firebase
        bookId = intent.getStringExtra("bookId") !!
        LoadBookDetails()

        //handle click, goback
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

    }

    private fun LoadBookDetails() {
        Log.d(TAG, "LoadBookDetails: Get pdf url from DB")

        val ref = FirebaseDatabase.getInstance().getReference("Books")
        ref.child(bookId)
            .addListenerForSingleValueEvent(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val pdfUrl = snapshot.child("url").value
                    Log.d(TAG,"onDataChange: PDF_URL: $pdfUrl")
                    //Step (2) Load pdf using url from firebase storage
                    loadBookFromUrl("$pdfUrl")
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
    }

    private fun loadBookFromUrl(pdfUrl: String) {
        val reference = FirebaseStorage.getInstance().getReferenceFromUrl(pdfUrl)
        reference.getBytes(Constants.MAX_BYTES_PDF)
            .addOnSuccessListener {bytes ->
                binding.pdfView.fromBytes(bytes)
                    .swipeHorizontal(false)
                    .onPageChange { page, pageCount ->
                        //set current and total pages in toolbar subtitle
                        val currentPage = page+1 // page starts from 0 so do +1 to start from 1
                        binding.toolbarSubtitleTv.text = "$currentPage/$pageCount" //e.g. 3/232
                    }
                    .onError { t->
                        Log.d(TAG, "loadBookFromUrl: ${t.message}")
                    }
                    .onPageError { page, t ->
                        Log.d(TAG, "loadBookFromUrl: ${t.message}")
                    }
                    .load()
                binding.progressBar.visibility = View.GONE
            }
            .addOnFailureListener {e ->
                Log.d(TAG, "loadBookFromUrl: ${e.message}")
                binding.progressBar.visibility = View.GONE
            }
    }
}
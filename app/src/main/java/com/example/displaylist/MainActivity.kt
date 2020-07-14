package com.example.displaylist

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Adapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import kotlinx.android.synthetic.main.doc_view.view.*

class MainActivity : AppCompatActivity() {


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item!!.itemId
        if (id == R.id.refresh) {
        }
        return super.onOptionsItemSelected(item)
    }


    lateinit var reView: RecyclerView
    lateinit var myRef: DatabaseReference
    private var mAdapter: FirebaseRecyclerAdapter<document, viewHolderDocument>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        reView = findViewById(R.id.recycleView)
        myRef = FirebaseDatabase.getInstance().getReference("documents")


        val layoutManager = LinearLayoutManager(this)
        reView.layoutManager = layoutManager


        docRecycle()
    }

    private fun docRecycle() {
        val query: Query = myRef.orderByChild("id")

         mAdapter =
            object : FirebaseRecyclerAdapter<document, viewHolderDocument>(
                document::class.java,
                R.layout.doc_view,
                viewHolderDocument::class.java,
                query
            ) {

                override fun populateViewHolder(p0: viewHolderDocument?, p1: document?, p2: Int) {
                    if (p0 != null) {
                        if (p1 != null) {
                            p0.itemView.tvName.setText(p1.name)
                        }
                    }
                    if (p0 != null) {
                        if (p1 != null) {
                            p0.itemView.tvId.setText("" + p1.id)
                        }
                    }


                }

            }

        reView.adapter = mAdapter

    }

    override fun onDestroy() {
        mAdapter?.cleanup()
        super.onDestroy()
    }

}
    class viewHolderDocument(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var txt_name: TextView
        internal var txt_id: TextView

        init {
            txt_name = itemView.findViewById<TextView>(R.id.tvName)
            txt_id = itemView.findViewById<TextView>(R.id.tvId)

        }


    }

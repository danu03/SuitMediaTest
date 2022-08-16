package com.danusuhendra.suitmediatest.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.danusuhendra.suitmediatest.R
import com.danusuhendra.suitmediatest.adapter.EventAdapter
import com.danusuhendra.suitmediatest.databinding.ActivityEventBinding
import com.danusuhendra.suitmediatest.model.Event
import com.danusuhendra.suitmediatest.utils.NAME

class EventActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEventBinding
    private lateinit var adapter: EventAdapter
    private lateinit var layoutManager: LinearLayoutManager

    private lateinit var list: ArrayList<Event>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initToolbar()

        list = arrayListOf(Event("Toko Kue",
            R.drawable.img,"Sa dasd asdasdas asdasd asd","15 Januari 2022","15:00 PM",1,1))
        list.add(Event("Toko Ikan",R.drawable.img_3,"asd asdasd asd askldjasljdas asdjlaskjdasd","15 Januari 2022","15:00 PM",1,1))
        list.add(Event("Toko Komputer",R.drawable.img_1,"asd as asdasd asdasdaslkdas asdasd","15 Januari 2022","15:00 PM",1,1))
        list.add(Event("Toko Makanan",R.drawable.img_2,"asd adfgdg sedfcas qwed asdqwe dasd","15 Januari 2022","15:00 PM",1,1))
        list.add(Event("Toko Baju",R.drawable.img_3,"asdasdfser fsadfasfsdf sdfasf","15 Januari 2022","15:00 PM",1,1))
        list.add(Event("Toko Sepeda",R.drawable.img_2,"asdas dasfsda fweaf sdf ase dsaf asd","15 Januari 2022","15:00 PM",1,1))

        layoutManager = LinearLayoutManager(this)
        setUpRecyclerView()
        adapter.setList(list)
        adapter.setClickCallback(object : EventAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Event) {
                val intent = intent
                intent.putExtra(NAME, data.name)
                setResult(RESULT_OK, intent)
                finish()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.event_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.action_search -> Toast.makeText(this,"Search Selected",Toast.LENGTH_SHORT).show()
            R.id.action_nav_maps -> Toast.makeText(this,"Maps Selected",Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initToolbar() {
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Event"
    }

    private fun setUpRecyclerView() {
        adapter = EventAdapter()
        binding.apply {
            rvEvent.layoutManager = layoutManager
            rvEvent.setHasFixedSize(true)
            rvEvent.adapter = adapter
        }
    }
}
package ad.cfp.androidpobprofinal2.ui

import ad.cfp.androidpobprofinal2.MainActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ad.cfp.androidpobprofinal2.R
import ad.cfp.androidpobprofinal2.adapter.ActionsRvAdapter
import ad.cfp.androidpobprofinal2.adapter.CatalegAdapter
import ad.cfp.androidpobprofinal2.data.Cataleg
import ad.cfp.androidpobprofinal2.databinding.FragmentFirstBinding
import ad.cfp.androidpobprofinal2.utils.CONSTANTS
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Firebase
import com.google.firebase.crashlytics.crashlytics
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class FirstFragment : Fragment(), ActionsRvAdapter {
    var _binding: FragmentFirstBinding?=null
    val binding get() = _binding!!
    val llista = mutableListOf<Cataleg>()
    val catalegAdapter= CatalegAdapter(llista, this)
    val db= Firebase.firestore
    val crash=Firebase.crashlytics
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding= FragmentFirstBinding.inflate(inflater, container, false)
        binding.rv.apply {
            adapter=catalegAdapter
            layoutManager= LinearLayoutManager(requireActivity())
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        loading(true)
        llista.clear()
        catalegAdapter.notifyDataSetChanged()
        db.collection(CONSTANTS.CATALEG).get()
            .addOnCompleteListener { loading(false)}
            .addOnFailureListener {e->
                Log.e("Firebase", e.toString())
                crash.recordException(e)
            }
            .addOnSuccessListener { documents->
                for (document in documents){
                    val item = document.toObject<Cataleg>()
                    llista.add(item)
                    catalegAdapter.notifyItemInserted(llista.size-1)
                }
                if(llista.isEmpty()){
                    binding.rv.visibility=View.GONE
                    binding.listEmpty.root.visibility=View.VISIBLE
                    Log.d("llista", "La llista està buida")
                } else {
                    binding.rv.visibility=View.VISIBLE
                    binding.listEmpty.root.visibility=View.GONE
                }
            }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
    fun loading(estat:Boolean){
        if(estat){
            binding.loading.root.visibility=View.VISIBLE
        } else {
            binding.loading.root.visibility=View.GONE
        }
    }

    override fun fav(position: Int, item: Cataleg) {
        loading(true)
        val idx = llista.indexOfFirst { element-> element.id==item.id }
        item.favorite=!item.favorite
        llista[idx]=item
        db.collection(CONSTANTS.CATALEG).document(item.id).set(item)
            .addOnSuccessListener {catalegAdapter.notifyItemChanged(position)}
            .addOnFailureListener { e->
                Log.e("firebase", e.toString())
                crash.recordException(e)
            }
            .addOnCompleteListener { loading(false) }
    }

    override fun edit(item: Cataleg) {
        val activity=requireActivity() as MainActivity
        activity.item=item
        activity.binding.viewpager2.currentItem=2
    }
}
package ad.cfp.androidpobprofinal2.ui

import ad.cfp.androidpobprofinal2.MainActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ad.cfp.androidpobprofinal2.R
import ad.cfp.androidpobprofinal2.data.Cataleg
import ad.cfp.androidpobprofinal2.databinding.FragmentThirdBinding
import ad.cfp.androidpobprofinal2.utils.CONSTANTS
import android.util.Log
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.crashlytics.crashlytics
import com.google.firebase.firestore.firestore

class ThirdFragment : Fragment() {
    var _binding: FragmentThirdBinding?=null
    val binding get() = _binding!!
    val db= Firebase.firestore
    val crash= Firebase.crashlytics
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding= FragmentThirdBinding.inflate(inflater, container, false)
        binding.btnSend.setOnClickListener { sendData() }
        return binding.root
    }
    //
    //

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
    fun sendData(){
        loading(true)
        val isCorrect=checkDades()
        if(!isCorrect){
            loading(false)
            return
        }
        val nom=binding.tilNom.editText!!.text.toString()
        val categoria=binding.tilCategoria.editText!!.text.toString()
        val descripcio=binding.tilDescripcio.editText!!.text.toString()
        val valor=binding.tilValor.editText!!.text.toString().toDouble()
        val isFavorite=binding.switchFav.isChecked

        val item= Cataleg(nom, categoria, descripcio, valor, isFavorite)

        db.collection(CONSTANTS.CATALEG).add(item)
            .addOnSuccessListener {Toast.makeText(requireActivity(), "Dades amb nom $nom afegides", Toast.LENGTH_SHORT).show()
            clearData()}
            .addOnCompleteListener {
                loading(false)
            }
            .addOnFailureListener { e->
                Log.e("Firestore", e.toString())
                crash.recordException(e)
            }
    }
    fun checkDades(): Boolean{
        val nom=binding.tilNom.editText!!.text.toString()
        val categoria=binding.tilCategoria.editText!!.text.toString()
        val descripcio=binding.tilDescripcio.editText!!.text.toString()
        val valor=binding.tilValor.editText!!.text.toString()
        if(nom.isEmpty()){
            return false
        }
        if(categoria.isEmpty()){
            return false
        }
        if(descripcio.isEmpty()){
            return false
        }
        if(valor.isEmpty()){
            return false
        }
        return true
    }
    fun loading(estat:Boolean){
        if(estat){
            binding.loading.root.visibility=View.VISIBLE
        } else {
            binding.loading.root.visibility=View.GONE
        }
    }
    fun clearData(){
        val nom=binding.tilNom.editText!!.setText("")
        val categoria=binding.tilCategoria.editText!!.setText("")
        val descripcio=binding.tilDescripcio.editText!!.setText("")
        val valor=binding.tilValor.editText!!.setText("")
    }
}
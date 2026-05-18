package ad.cfp.androidpobprofinal2

import ad.cfp.androidpobprofinal2.data.Cataleg
import ad.cfp.androidpobprofinal2.databinding.ActivityMainBinding
import ad.cfp.androidpobprofinal2.ui.FirstFragment
import ad.cfp.androidpobprofinal2.ui.SecondFragment
import ad.cfp.androidpobprofinal2.ui.ThirdFragment
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val llista=mutableListOf<Fragment>(
            FirstFragment(),
            SecondFragment(),
            ThirdFragment()
        )
        binding.viewpager2.apply{
            adapter= MainPagerView(this@MainActivity, llista)
            orientation= ViewPager2.ORIENTATION_HORIZONTAL
        }
    }
}
class MainPagerView(activity: MainActivity,val llista: List<Fragment>): FragmentStateAdapter(activity){
    override fun createFragment(p0: Int): Fragment {
        return llista[p0]
    }

    override fun getItemCount(): Int {
         return llista.size
    }

}
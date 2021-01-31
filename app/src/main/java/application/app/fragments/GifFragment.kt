package application.app.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import application.R
import application.data.entity.Gif
import application.network.Repository
import application.network.RepositoryProvider
import kotlinx.android.synthetic.main.gif_fragment.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class GifFragment : Fragment(), View.OnClickListener {

    companion object {
        private val TAG = GifFragment::class.java.simpleName

        const val MIN_ID = 1
        const val MAX_ID = 2500
    }

    private lateinit var mImageView: ImageView
    private lateinit var repository : Repository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.gif_fragment, container, false)
        mImageView = view.img
        view.retry_btn.setOnClickListener(this)
        view.load_btn.setOnClickListener(this)
        view.back_btn.setOnClickListener(this)
        view.back_btn.isEnabled = false
        return  view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        loadGif()
        view.back_btn.isEnabled = true
        super.onViewCreated(view, savedInstanceState)

    }

    private fun loadGif() {
        view?.load_back_layout?.visibility = View.GONE
        view?.decr?.visibility = View.GONE
        view?.retry_layout?.visibility = View.GONE
        view?.waiter?.visibility = View.VISIBLE
        view?.img?.visibility = View.GONE
        repository = RepositoryProvider
            .provideRepository()

        val randomInteger = (MIN_ID..MAX_ID).shuffled().first()

        val call: Call<Gif> = repository.getGifInfo(randomInteger)
        call.enqueue(object: Callback<Gif> {

            override fun onFailure(call: Call<Gif>, t: Throwable) {
                Log.e(TAG, t.toString())
                view?.retry_layout?.visibility = View.VISIBLE
                view?.waiter?.visibility = View.GONE
                view?.load_back_layout?.visibility = View.GONE
            }
            override fun onResponse(call: Call<Gif>, response: Response<Gif>) {
                if (response.isSuccessful) {
                    val gifInfo = response.body()
                    view?.let {
                        Glide
                            .with(it)
                            .load(gifInfo!!.gifURL)
                            .into(mImageView)
                    }
                    if (gifInfo != null) {
                        view?.decr?.text = gifInfo.description
                    }
                    view?.load_back_layout?.visibility = View.VISIBLE
                    view?.decr?.visibility = View.VISIBLE
                    view?.img?.visibility = View.VISIBLE
                } else {
                    view?.retry_layout?.visibility = View.VISIBLE
                    Log.e(GifFragment::class.java.simpleName, "Error happened while loading gif info")
                }
                view?.waiter?.visibility = View.GONE
            }
        }
        )
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.retry_btn -> {
                loadGif()
            }
            R.id.load_btn -> {
                loadGif()
            }
            R.id.back_btn -> {
            }
        }
    }
}

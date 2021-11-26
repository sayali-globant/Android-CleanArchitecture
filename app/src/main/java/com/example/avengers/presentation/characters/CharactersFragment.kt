package com.example.avengers.presentation.characters

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.avengers.R
import com.example.avengers.common.listeners.OnLoadMoreListener
import com.example.avengers.domain.model.AvengerCharacterResponse
import com.example.avengers.domain.model.Result
import com.example.avengers.domain.model.request.CharactersRequest
import com.example.avengers.domain.utils.isNetworkAvailable
import com.example.avengers.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_characters.*
import org.koin.android.viewmodel.ext.android.viewModel


class CharactersFragment : BaseFragment(), OnLoadMoreListener {

    private val mCharactersViewModel: CharactersViewModel by viewModel()
   private var mCharactersAdapter: CharactersAdapter? = null
    private var mView : View? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        if (mView == null) {
            mView =  inflater.inflate(R.layout.fragment_characters, container, false)
        }
        return mView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observers()
        setupUI()
        callApi()

    }

    private fun callApi(){
        if (requireActivity().isNetworkAvailable()) {
            mCharactersViewModel.getCharactersList(CharactersRequest(offset = 0))
        } else {
            Toast.makeText(
                requireActivity(),
                getString(R.string.no_internet_connection),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun setupUI() {
        if (mCharactersAdapter == null) {
            recyclerViewCharacters.layoutManager = LinearLayoutManager(requireContext())
            mCharactersAdapter = CharactersAdapter(arrayListOf(),this)
            recyclerViewCharacters.addItemDecoration(
                DividerItemDecoration(
                    recyclerViewCharacters.context,
                    (recyclerViewCharacters.layoutManager as LinearLayoutManager).orientation
                )
            )
            recyclerViewCharacters.adapter = mCharactersAdapter
        }
    }

    private fun observers(){
        with(mCharactersViewModel) {

            postsData.observe(requireActivity(), Observer {
                // activityPostsBinding.postsProgressBar.visibility = GONE
                setData(it)
                Log.d("","MY "+ it.status)
            })

            messageData.observe(requireActivity(), Observer {
                Toast.makeText(requireActivity(), it, LENGTH_LONG).show()
            })

            showProgressbar.observe(requireActivity(), Observer { isVisible ->
              //  progress_circular.visibility = if (isVisible) VISIBLE else GONE
            })
        }
    }

    private fun setData(avengerCharacterResponse: AvengerCharacterResponse) {
        avengerCharacterResponse.mainData?.results?.let {
            renderList(it)
        }

    }

    private fun renderList(list: List<Result>) {
        mCharactersAdapter?.addData(list)
        mCharactersAdapter?.notifyDataSetChanged()
    }

    companion object {

        fun newInstance() = CharactersFragment()
    }

    override fun onLoadMore(position: Int) {

    }
}
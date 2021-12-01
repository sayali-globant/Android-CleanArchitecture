package com.example.marvels.presentation.characters

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.marvels.R
import com.example.marvels.common.listeners.OnActionListener
import com.example.marvels.databinding.FragmentCharactersBinding
import com.example.marvels.presentation.MainActivity
import com.example.marvels.presentation.base.BaseFragment
import com.example.marvels.presentation.characterdetail.CharacterDetailFragment
import com.marvel.data.characters.model.CharacterDetail
import com.marvel.data.characters.model.MarvelCharacterResponse
import com.marvel.data.characters.model.request.CharactersRequest
import com.marvel.mydomain.Status


class CharactersFragment : BaseFragment(), OnActionListener {

    private val mCharactersViewModel: CharactersViewModel by activityViewModels()
    private var mCharactersAdapter: CharactersAdapter? = null
    private var mView: View? = null
    private var mOffset = 0
    private lateinit var mBinding: FragmentCharactersBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        if (mView == null) {
            mBinding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_characters, container, false)
            mView = mBinding.root
        }
        return mView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observers()
        setupUI()
        if (mOffset == 0) {
            callApi()
        }
    }

    private fun callApi() {
        mCharactersViewModel.getCharactersList(
            CharactersRequest(
                offset = mOffset
            )
        )
        mOffset++
    }

    private fun setupUI() {
        if (mCharactersAdapter == null) {
            mBinding.apply {
                recyclerViewCharacters.layoutManager = LinearLayoutManager(requireContext())
                mCharactersAdapter = CharactersAdapter(arrayListOf(), this@CharactersFragment)
                recyclerViewCharacters.addItemDecoration(
                    DividerItemDecoration(
                        recyclerViewCharacters.context,
                        (recyclerViewCharacters.layoutManager as LinearLayoutManager).orientation
                    )
                )
                recyclerViewCharacters.adapter = mCharactersAdapter
            }

        }
    }

    private fun observers() {
        with(mCharactersViewModel) {
            mCharactersResponse.observe(requireActivity(), Observer {

                when (it.status) {

                    Status.LOADING -> {
                        mBinding.progressCharacters.visibility = View.VISIBLE
                        mBinding.recyclerViewCharacters.visibility = View.GONE
                    }

                    Status.SUCCESS -> {
                        mBinding.progressCharacters.visibility = View.GONE
                        setData(it.data!!)
                        mBinding.recyclerViewCharacters.visibility = View.VISIBLE
                        Log.d("", "MY " + it.status)

                    }

                    Status.ERROR -> {
                        Toast.makeText(requireActivity(), it.message, LENGTH_LONG).show()
                        mBinding.progressCharacters.visibility = View.GONE
                    }
                }
            })
        }
    }

    private fun setData(avengerCharacterResponse: MarvelCharacterResponse) {
        avengerCharacterResponse.mainData?.results?.let {
            renderList(it)
        }
    }

    private fun renderList(list: List<CharacterDetail>) {
        mCharactersAdapter?.addData(list)
        mCharactersAdapter?.notifyDataSetChanged()
    }

    companion object {
        fun newInstance() = CharactersFragment()
    }

    override fun onActionListener(position: Int, type: Int, characterDetail: CharacterDetail) {
        when (type) {
            CharactersAdapter.ACTION_ITEM_CLICK -> {
                (requireActivity() as MainActivity).replaceFragment(
                    CharacterDetailFragment.newInstance(
                        characterDetail
                    )
                )
            }
            CharactersAdapter.ACTION_LOAD_MORE -> {
                callApi()
            }
        }
    }
}

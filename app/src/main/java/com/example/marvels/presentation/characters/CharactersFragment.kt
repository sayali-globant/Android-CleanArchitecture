package com.example.marvels.presentation.characters

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.marvels.R
import com.example.marvels.common.listeners.OnActionListener
import com.example.marvels.databinding.FragmentCharactersBinding
import com.example.marvels.domain.utils.isNetworkAvailable
import com.example.marvels.domain.utils.toast
import com.example.marvels.presentation.base.BaseFragment
import com.marvel.domain.Status
import com.marvel.domain.model.CharacterModel
import com.marvel.domain.model.CharactersRequestModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharactersFragment : BaseFragment(), OnActionListener {


    private val mCharactersViewModel: CharactersViewModel by activityViewModels()
    private var mCharactersAdapter: CharactersAdapter? = null
    private lateinit var mBinding: FragmentCharactersBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        callApi()
        observers()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_characters, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()

    }

    override fun onActionListener(position: Int, type: Int, characterDetail: CharacterModel) {
        when (type) {
            CharactersAdapter.ACTION_ITEM_CLICK -> {
                val characterId = characterDetail.id!!
                val direction = CharactersFragmentDirections.toCharacterDetailsFragment(characterId)
                findNavController().navigate(direction)
            }
            CharactersAdapter.ACTION_LOAD_MORE -> {
                callApi()
            }
        }
    }

    private fun callApi() {
        if (requireActivity().isNetworkAvailable()) {
            mCharactersViewModel.getCharactersList(CharactersRequestModel())
        } else {
            getString(R.string.no_internet).toast(requireContext())
        }
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
            mCharactersResponse.observe(requireActivity(), {

                when (it.status) {

                    Status.LOADING -> {
                        mBinding.progressCharacters.visibility = View.VISIBLE
                        mBinding.recyclerViewCharacters.visibility = View.GONE
                    }

                    Status.SUCCESS -> {
                        mBinding.progressCharacters.visibility = View.GONE
                        setData(it.data)
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

    private fun setData(data: List<CharacterModel>?) {
        data?.let {
            renderList(it)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun renderList(list: List<CharacterModel>) {
        mCharactersAdapter?.let {
            it.addData(list)
            it.notifyDataSetChanged()
        }
    }



}

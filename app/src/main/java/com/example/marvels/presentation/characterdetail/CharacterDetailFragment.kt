package com.example.marvels.presentation.characterdetail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.example.marvels.R
import com.example.marvels.databinding.FragmentCharacterDetailBinding
import com.example.marvels.domain.utils.isNetworkAvailable
import com.example.marvels.domain.utils.toast
import com.example.marvels.presentation.base.BaseFragment
import com.marvel.data.characters.model.CharacterDetail
import com.marvel.data.characters.model.request.CharactersRequest
import com.marvel.domain.Status


class CharacterDetailFragment : BaseFragment() {

    companion object {

        private const val ARG_CHARACTER_ID = "character_id"
        fun newInstance(id: Int) =
            CharacterDetailFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_CHARACTER_ID, id)
                }
            }
    }

    private val mCharactersDetailViewModel: CharacterDetailViewModel by activityViewModels()

    private var mCharacterDetailId: Int? = null
    private lateinit var mBinding: FragmentCharacterDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getArgs()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_character_detail,
            container,
            false
        )
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        callApi()
        observers()
    }

    private fun callApi() {
        if (requireActivity().isNetworkAvailable()) {
            mCharactersDetailViewModel.getCharacterDetails(
                CharactersRequest(id = mCharacterDetailId?.toString()!!)
            )
        } else {
            getString(R.string.no_internet).toast(requireContext())
        }
    }

    private fun observers() {
        with(mCharactersDetailViewModel) {
            mCharacterDetails.observe(requireActivity(), {

                when (it.status) {

                    Status.LOADING -> {
                        mBinding.progressCharacterDetails.visibility = View.VISIBLE

                    }

                    Status.SUCCESS -> {
                        mBinding.progressCharacterDetails.visibility = View.GONE
                        setupUI(it.data?.mainData?.results?.get(0))
                        Log.d("", "MY " + it.status)

                    }

                    Status.ERROR -> {
                        it.message?.toast(requireContext())
                        mBinding.progressCharacterDetails.visibility = View.GONE
                    }
                }
            })
        }
    }

    private fun setupUI(characterDetail: CharacterDetail?) {
        characterDetail?.let {
            mBinding.apply {
                it.thumbnail?.let { thumb ->
                    imageUrl = thumb.path + "." + thumb.extension
                }
                character = it
            }
        }
    }

    private fun getArgs() {
        arguments?.let {
            mCharacterDetailId = it.getInt(ARG_CHARACTER_ID)
        }
    }


}

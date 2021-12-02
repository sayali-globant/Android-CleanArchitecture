package com.example.marvels.presentation.characterdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.marvels.R
import com.example.marvels.databinding.FragmentCharacterDetailBinding
import com.example.marvels.presentation.base.BaseFragment
import com.marvel.data.characters.model.CharacterDetail


class CharacterDetailFragment : BaseFragment() {

    companion object {

        private const val ARG_CHARACTER = "character"
        fun newInstance(characterDetail: CharacterDetail) =
            CharacterDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_CHARACTER, characterDetail)
                }
            }
    }

    private var mCharacterDetail: CharacterDetail? = null
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
        setupUI()
    }

    private fun setupUI() {
        mCharacterDetail?.let {
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
            mCharacterDetail = it.getParcelable(ARG_CHARACTER)
        }
    }


}

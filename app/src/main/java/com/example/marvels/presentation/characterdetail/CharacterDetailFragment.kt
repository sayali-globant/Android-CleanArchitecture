package com.example.marvels.presentation.characterdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.marvels.R
import com.example.marvels.data.entity.CharacterDetail
import com.example.marvels.databinding.FragmentCharacterDetailBinding
import com.example.marvels.presentation.base.BaseFragment


class CharacterDetailFragment : BaseFragment() {

    private var mView: View? = null
    private var mCharacterDetail: CharacterDetail? = null
    private lateinit var mBinding: FragmentCharacterDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getArgs()
    }

    private fun getArgs() {
        arguments?.let {
            mCharacterDetail = it.getParcelable(ARG_CHARACTER)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        if (mView == null) {
            mBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_character_detail,
                container,
                false
            )
            mView = mBinding.root
        }
        return mView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
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

    companion object {

        private const val ARG_CHARACTER = "character"
        fun newInstance(characterDetail: CharacterDetail) =
            CharacterDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_CHARACTER, characterDetail)
                }
            }
    }

}

package com.example.marvels.presentation.characters

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.marvels.R
import com.example.marvels.common.listeners.OnActionListener
import com.example.marvels.data.entity.AvengerCharacterResponse
import com.example.marvels.data.entity.CharacterDetail
import com.example.marvels.data.entity.request.CharactersRequest
import com.example.marvels.databinding.FragmentCharactersBinding
import com.example.marvels.domain.utils.isNetworkAvailable
import com.example.marvels.presentation.MainActivity
import com.example.marvels.presentation.base.BaseFragment
import com.example.marvels.presentation.characterdetail.CharacterDetailFragment
import org.koin.android.viewmodel.ext.android.viewModel

class CharactersFragment : BaseFragment(), OnActionListener {

    private val mCharactersViewModel: CharactersViewModel by viewModel()
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
        if (requireActivity().isNetworkAvailable()) {
            mCharactersViewModel.getCharactersList(CharactersRequest(offset = mOffset))
            mOffset++
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
            mCharactersData.observe(requireActivity(), Observer {
                // mPrgressDialog.hide()
                mBinding.progressCharacters.visibility = View.GONE
                setData(it)
                Log.d("", "MY " + it.status)
            })

            mErrorData.observe(requireActivity(), {
                //  mPrgressDialog?.hide()
                Toast.makeText(requireActivity(), it, LENGTH_LONG).show()
            })

            mShowProgressbar.observe(requireActivity(), Observer { isVisible ->
                if (isVisible) {
                    mBinding.progressCharacters.visibility = View.VISIBLE
                    // mPrgressDialog?.show()
                } else {
                    mBinding.progressCharacters.visibility = View.GONE
                    //  mPrgressDialog?.hide()
                }
            })
        }
    }

    private fun setData(avengerCharacterResponse: AvengerCharacterResponse) {
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

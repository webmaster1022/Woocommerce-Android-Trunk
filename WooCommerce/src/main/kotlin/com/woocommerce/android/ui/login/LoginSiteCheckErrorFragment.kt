package com.woocommerce.android.ui.login

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.woocommerce.android.R
import com.woocommerce.android.databinding.FragmentLoginSiteCheckErrorBinding
import com.woocommerce.android.ui.login.UnifiedLoginTracker.Click
import com.woocommerce.android.ui.login.UnifiedLoginTracker.Step
import dagger.hilt.android.AndroidEntryPoint
import org.wordpress.android.login.LoginListener
import javax.inject.Inject

@AndroidEntryPoint
class LoginSiteCheckErrorFragment : Fragment(R.layout.fragment_login_site_check_error) {
    companion object {
        const val TAG = "LoginGenericErrorFragment"
        const val ARG_SITE_ADDRESS = "SITE-ADDRESS"
        const val ARG_ERROR_MESSAGE = "ERROR-MESSAGE"

        fun newInstance(siteAddress: String, errorMsg: String): LoginSiteCheckErrorFragment {
            val fragment = LoginSiteCheckErrorFragment()
            val args = Bundle()
            args.putString(ARG_SITE_ADDRESS, siteAddress)
            args.putString(ARG_ERROR_MESSAGE, errorMsg)
            fragment.arguments = args
            return fragment
        }
    }

    @Inject lateinit var unifiedLoginTracker: UnifiedLoginTracker
    private var loginListener: LoginListener? = null
    private var siteAddress: String? = null
    private var errorMsg: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            siteAddress = it.getString(LoginJetpackRequiredFragment.ARG_SITE_ADDRESS, null)
            errorMsg = it.getString(ARG_ERROR_MESSAGE, null)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        activity?.title = getString(R.string.log_in)

        val binding = FragmentLoginSiteCheckErrorBinding.bind(view)
        val btnBinding = binding.loginEpilogueButtonBar!!

        val toolbar = view.findViewById(R.id.toolbar) as Toolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        (activity as AppCompatActivity).supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowTitleEnabled(true)
        }

        binding.loginErrorMsg.text = errorMsg

        with(btnBinding.buttonPrimary) {
            visibility = View.VISIBLE
            text = getString(R.string.login_try_another_store)
            setOnClickListener {
                unifiedLoginTracker.trackClick(Click.TRY_ANOTHER_STORE)

                requireActivity().onBackPressed()
            }
        }

        with(btnBinding.buttonSecondary) {
            visibility = View.VISIBLE
            text = getString(R.string.login_try_another_account)
            setOnClickListener {
                unifiedLoginTracker.trackClick(Click.TRY_ANOTHER_ACCOUNT)

                loginListener?.startOver()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_login, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.help) {
            unifiedLoginTracker.trackClick(Click.SHOW_HELP)
            loginListener?.helpSiteAddress(siteAddress)
            return true
        }

        return false
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // this will throw if parent activity doesn't implement the login listener interface
        loginListener = context as? LoginListener
    }

    override fun onDetach() {
        loginListener = null
        super.onDetach()
    }

    override fun onResume() {
        super.onResume()

        unifiedLoginTracker.track(step = Step.NOT_WORDPRESS_SITE)
    }
}

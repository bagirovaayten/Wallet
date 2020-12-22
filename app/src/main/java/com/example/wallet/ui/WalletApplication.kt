package com.example.wallet.ui

import android.app.Application
import com.example.wallet.data.db.WalletDataBase
import com.example.wallet.repostory.WalletRepository
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class WalletApplication: Application(), KodeinAware {

    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@WalletApplication))
        bind() from singleton { WalletDataBase(instance()) }
        bind() from singleton {
            WalletRepository(
                instance()
            )
        }
        bind() from provider {
            WalletWievModelFactory(
                instance()
            )
        }
    }
}
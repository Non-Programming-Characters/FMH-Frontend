package ru.solomka.fmh.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ru.solomka.fmh.app.core.api.CardApi
import ru.solomka.fmh.app.core.api.RetrofitClient
import ru.solomka.fmh.app.core.api.UserApi
import ru.solomka.fmh.app.core.dto.Entity
import ru.solomka.fmh.app.core.dto.card.CardDto
import ru.solomka.fmh.app.core.repository.NetworkRepository
import ru.solomka.fmh.app.core.service.CardService
import ru.solomka.fmh.app.core.service.impl.CardServiceImpl
import ru.solomka.fmh.app.fragment.FragmentHome
import ru.solomka.fmh.app.fragment.FragmentProfile
import ru.solomka.fmh.app.fragment.FragmentQuiz
import ru.solomka.fmh.app.network.NetworkMonitor
import ru.solomka.fmh.app.utils.Cache
import ru.solomka.fmh.app.utils.impl.CardConcurrentCache
import ru.solomka.fmh.app.utils.impl.UserTestConcurrentCache
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.UUID

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.main_activity)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)

        loadFragment(FragmentHome())

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> loadFragment(FragmentHome())
                R.id.nav_quiz -> loadFragment(FragmentQuiz())
                R.id.nav_profile -> loadFragment(FragmentProfile())
            }
            true
        }

        val networkRepository: NetworkRepository = NetworkMonitor(this)

        val cardService: CardService = CardServiceImpl(networkRepository = networkRepository)
        if(networkRepository.hasInternetConnection()) {
            val cards: List<CardDto> = cardService.findAllCards()
            // TODO: Сохранять в "кэш" и потом из него дергать
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    companion object {
        val USER_AID_API: UserApi = RetrofitClient().getApiClient(UserApi::class.java)
        val CARD_AID_API: CardApi = RetrofitClient().getApiClient(CardApi::class.java)

        val USER_DATA_CACHE: Cache<UUID, Entity> = UserTestConcurrentCache()
        val CARD_DATA_CACHE: Cache<UUID, Entity> = CardConcurrentCache()
    }
}
package com.scope.application.remote

import android.location.Geocoder
import com.scope.application.domain.models.GeoAuto
import com.scope.application.domain.models.GeoVehicles
import io.mockk.MockKAnnotations
import io.mockk.mockk
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.mockk.coEvery
import io.mockk.coVerify
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.any
import org.junit.Before
import org.junit.Test

class ApplicationUseCaseImplTest {

    private var repository = mockk<ApplicationRepository>(relaxed = true)
    private var geoCoder = mockk<Geocoder>()
    var response = GeoVehicles(listOf(GeoAuto(0.0,0.0,0)))
    lateinit var useCaseImplTest: ApplicationUseCaseImpl


        @Before
        fun setup() {
            MockKAnnotations.init(this)
            useCaseImplTest = ApplicationUseCaseImpl(repository)
        }

    @Test
    fun `assert that getGeoVehicles insert addressValue in response`() {
        //given
        val mockedAddress = "MOCKED ADDRESS"

        coEvery { repository.requestDriverDetails(any())} returns response
        every { geoCoder.getFromLocation(any(),any(),any())[any()].getAddressLine(any()) } returns mockedAddress


        //then
        runBlocking {
            useCaseImplTest.getVehiclesGeo("1", geoCoder)
        }

        //should
        assert(response.geoAuto.find { it.address == mockedAddress }!= null)

    }
}

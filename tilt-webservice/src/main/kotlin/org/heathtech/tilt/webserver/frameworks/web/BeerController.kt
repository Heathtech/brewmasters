package org.heathtech.tilt.webserver.frameworks.web

import org.heathtech.tilt.webserver.domain.data.beer.BeerRepository
import org.heathtech.tilt.webserver.domain.data.beer.CreateBeerRequest
import org.heathtech.tilt.webserver.domain.data.beer.UpdateBeerRequest
import org.heathtech.tilt.webserver.domain.data.tilt.TiltRepository
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime
import java.util.UUID

@RestController
@RequestMapping("beer")
class BeerController(
    val beerRepository: BeerRepository,
    val tiltRepository: TiltRepository,
) {
    @GetMapping("/list")
    fun getBeers() = beerRepository.getBeers()

    @GetMapping("/latest")
    fun getLatestBeer() = beerRepository.getBeers().lastOrNull()

    @PostMapping("")
    fun makeBeer(
        @RequestBody beerRequest: CreateBeerRequest,
    ) = beerRepository.storeBeer(beerRequest.toBeer(UUID.randomUUID()))

    @GetMapping("/{uuid}")
    fun getBeerById(
        @PathVariable uuid: UUID,
    ) = beerRepository.getBeerById(uuid)

    @PutMapping("/{uuid}")
    fun overwriteBeerById(
        @PathVariable uuid: UUID,
        @RequestBody beerRequest: CreateBeerRequest,
    ) = beerRepository.updateBeer(beerRequest.toBeer(uuid))

    @PatchMapping("/{uuid}")
    fun updateBeerById(
        @PathVariable uuid: UUID,
        @RequestBody beerRequest: UpdateBeerRequest,
    ) = beerRepository.updateBeer(
        beerRepository.getBeerById(uuid)?.let {
            it.copy(
                name = beerRequest.name ?: it.name,
                tiltDeviceId = beerRequest.tiltDeviceId ?: it.tiltDeviceId,
                startDate = beerRequest.startDate ?: it.startDate,
                endDate = beerRequest.endDate ?: it.endDate,
            )
        } ?: error("No beer with id $uuid"),
    )

    @DeleteMapping("/{uuid}")
    fun deleteBeerById(
        @PathVariable uuid: UUID,
    ) = beerRepository.removeBeer(uuid)

    @GetMapping("/{uuid}/data")
    fun tiltDataByBeer(
        @PathVariable uuid: UUID,
        @RequestParam("start", required = false) startDate: LocalDateTime?,
        @RequestParam("end", required = false) endDate: LocalDateTime?,
    ) = beerRepository.getBeerById(uuid)?.let {
        tiltRepository.getDataPoints(it.tiltDeviceId, startDate, endDate)
    } ?: error("No beer with id $uuid")
}

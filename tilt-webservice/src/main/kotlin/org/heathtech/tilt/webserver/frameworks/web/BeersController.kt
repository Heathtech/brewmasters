package org.heathtech.tilt.webserver.frameworks.web

import org.heathtech.tilt.webserver.domain.data.beer.BeerRepository
import org.heathtech.tilt.webserver.domain.data.beer.CreateBeerRequest
import org.heathtech.tilt.webserver.domain.data.beer.UpdateBeerRequest
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("beers")
class BeersController(
    val beerRepository: BeerRepository,
) {
    @GetMapping("")
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
}

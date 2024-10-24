package org.heathtech.tilt.webserver.frameworks.exposed.beer

import org.heathtech.tilt.webserver.domain.data.beer.Beer
import org.heathtech.tilt.webserver.domain.data.beer.BeerRepository
import org.heathtech.tilt.webserver.frameworks.exposed.tilt.TiltDeviceDao
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class ExposedBeerRepository : BeerRepository {
    override fun getBeers(): List<Beer> = transaction {
        BeerDao.all().map { it.toModel(null) } // TODO: Track end date?
    }

    override fun getBeerById(uuid: UUID): Beer? = transaction {
        BeerDao.findById(uuid)?.toModel(null) // TODO: Track end date?
    }

    override fun storeBeer(beer: Beer): Beer =
        transaction {
            BeerDao
                .new(id = beer.uuid) {
                    name = beer.name
                    tiltDevice =
                        requireNotNull(TiltDeviceDao.findById(beer.tiltDeviceId)) { "No tilt device found for ${beer.tiltDeviceId}" }
                    startedAt = beer.startDate
                }.toModel(null)
        }

    override fun updateBeer(beer: Beer): Beer {
        TODO("Not yet implemented")
    }

    override fun removeBeer(uuid: UUID): Beer {
        TODO("Not yet implemented")
    }
}

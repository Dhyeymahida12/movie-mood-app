package com.example.moviemood

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        lifecycleScope.launch(Dispatchers.IO) {
            val db = AppDatabase.getDatabase(applicationContext)
            val movieDao = db.movieDao()
            
            val prefs = getSharedPreferences("app_prefs", MODE_PRIVATE)
            val isPopulated = prefs.getBoolean("db_populated_v5", false)
            
            if (!isPopulated) {
                populateDefaultMovies(movieDao)
                prefs.edit().putBoolean("db_populated_v5", true).apply()
            }
        }

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }, 2000)
    }

    private suspend fun populateDefaultMovies(dao: MovieDao) {
        val movies = mutableListOf<MovieEntity>()
        
        // Action
        movies.add(MovieEntity(title = "Mad Max: Fury Road", directorYear = "George Miller - 2015", synopsis = "In a post-apocalyptic wasteland, a woman rebels against a tyrannical ruler.", rating = "⭐ 4.8", posterRes = R.drawable.mdmx, category = "Action"))
        movies.add(MovieEntity(title = "John Wick", directorYear = "Chad Stahelski - 2014", synopsis = "An ex-hit-man comes out of retirement to track down the gangsters that killed his dog.", rating = "⭐ 4.7", posterRes = R.drawable.image_9, category = "Action"))
        movies.add(MovieEntity(title = "The Dark Knight", directorYear = "Christopher Nolan - 2008", synopsis = "When the Joker wreaks havoc and chaos on Gotham, Batman must fight injustice.", rating = "⭐ 4.9", posterRes = R.drawable.image_10, category = "Action"))
        movies.add(MovieEntity(title = "Gladiator II", directorYear = "Ridley Scott - 2024", synopsis = "Lucius is forced to enter the Colosseum to return the glory of Rome to its people.", rating = "⭐ 4.5", posterRes = R.drawable.image_11, category = "Action"))
        movies.add(MovieEntity(title = "Top Gun: Maverick", directorYear = "Joseph Kosinski - 2022", synopsis = "Maverick is still pushing the envelope as a top naval aviator.", rating = "⭐ 4.8", posterRes = R.drawable.image_12, category = "Action"))
        movies.add(MovieEntity(title = "Mission: Impossible - Fallout", directorYear = "Christopher McQuarrie - 2018", synopsis = "Ethan Hunt and his IMF team must race against time after a mission goes wrong.", rating = "⭐ 4.7", posterRes = R.drawable.image_13, category = "Action"))
        movies.add(MovieEntity(title = "Avengers: Endgame", directorYear = "Anthony Russo - 2019", synopsis = "The Avengers assemble once more in order to restore stability to the universe.", rating = "⭐ 4.9", posterRes = R.drawable.image_14, category = "Action"))
        movies.add(MovieEntity(title = "Spider-Man: Across the Spider-Verse", directorYear = "Joaquim Dos Santos - 2023", synopsis = "Miles Morales catapults across the Multiverse, where he encounters a team of Spider-People.", rating = "⭐ 4.9", posterRes = R.drawable.image_15, category = "Action"))
        movies.add(MovieEntity(title = "The Matrix", directorYear = "Lana Wachowski - 1999", synopsis = "A computer hacker learns from mysterious rebels about the true nature of his reality.", rating = "⭐ 4.7", posterRes = R.drawable.image_16, category = "Action"))
        movies.add(MovieEntity(title = "Extraction", directorYear = "Sam Hargrave - 2020", synopsis = "Tyler Rake, a fearless black market mercenary, embarks on the most deadly extraction of his career.", rating = "⭐ 4.4", posterRes = R.drawable.image_17, category = "Action"))

        // Horror
        movies.add(MovieEntity(title = "The Conjuring", directorYear = "James Wan - 2013", synopsis = "Paranormal investigators Ed and Lorraine Warren work to help a family terrorized by a dark presence.", rating = "⭐ 4.5", posterRes = R.drawable.hrr, category = "Horror"))
        movies.add(MovieEntity(title = "A Quiet Place", directorYear = "John Krasinski - 2018", synopsis = "A family must live in silence while they hide from creatures that hunt by sound.", rating = "⭐ 4.6", posterRes = R.drawable.image, category = "Horror"))
        movies.add(MovieEntity(title = "Hereditary", directorYear = "Ari Aster - 2018", synopsis = "A grieving family is haunted by tragic and disturbing occurrences.", rating = "⭐ 4.3", posterRes = R.drawable.hereditary_h, category = "Horror"))
        movies.add(MovieEntity(title = "It", directorYear = "Andy Muschietti - 2017", synopsis = "In the summer of 1989, a group of bullied kids band together to destroy a shape-shifting monster.", rating = "⭐ 4.4", posterRes = R.drawable.image_2, category = "Horror"))
        movies.add(MovieEntity(title = "The Exorcist", directorYear = "William Friedkin - 1973", synopsis = "When a young girl is possessed by a mysterious entity, her mother seeks the help of two priests.", rating = "⭐ 4.7", posterRes = R.drawable.exorist, category = "Horror"))
        movies.add(MovieEntity(title = "Get Out", directorYear = "Jordan Peele - 2017", synopsis = "A young African-American visits his white girlfriend's parents for the weekend, where his uneasiness reaches a boiling point.", rating = "⭐ 4.6", posterRes = R.drawable.getout, category = "Horror"))
        movies.add(MovieEntity(title = "Scream", directorYear = "Wes Craven - 1996", synopsis = "A year after the murder of her mother, a teenage girl is terrorized by a new killer.", rating = "⭐ 4.2", posterRes = R.drawable.scream, category = "Horror"))
        movies.add(MovieEntity(title = "The Shining", directorYear = "Stanley Kubrick - 1980", synopsis = "A family heads to an isolated hotel for the winter where a sinister presence influences the father into violence.", rating = "⭐ 4.8", posterRes = R.drawable.image_6, category = "Horror"))
        movies.add(MovieEntity(title = "Halloween", directorYear = "John Carpenter - 1978", synopsis = "Fifteen years after murdering his sister, Michael Myers escapes from a mental hospital and returns to Haddonfield.", rating = "⭐ 4.5", posterRes = R.drawable.helloween, category = "Horror"))
        movies.add(MovieEntity(title = "Insidious", directorYear = "James Wan - 2010", synopsis = "A family looks to prevent evil spirits from trapping their comatose child in a realm called The Further.", rating = "⭐ 4.3", posterRes = R.drawable.incidious, category = "Horror"))

        // Comedy
        movies.add(MovieEntity(title = "The Hangover", directorYear = "Todd Phillips - 2009", synopsis = "Three buddies wake up from a Vegas bachelor party with no memory and the bachelor missing.", rating = "⭐ 4.4", posterRes = R.drawable.images_14, category = "Comedy"))
        movies.add(MovieEntity(title = "Grown Ups", directorYear = "Dennis Dugan - 2010", synopsis = "Five good friends and former teammates reunite for a Fourth of July holiday weekend.", rating = "⭐ 4.0", posterRes = R.drawable.image_19, category = "Comedy"))
        movies.add(MovieEntity(title = "Step Brothers", directorYear = "Adam McKay - 2008", synopsis = "Two middle-aged losers living at home are forced to become roommates when their parents marry.", rating = "⭐ 4.2", posterRes = R.drawable.image_20, category = "Comedy"))
        movies.add(MovieEntity(title = "Superbad", directorYear = "Greg Mottola - 2007", synopsis = "Two co-dependent high school seniors are forced to deal with separation anxiety after their plan to stage a booze-soaked party goes awry.", rating = "⭐ 4.5", posterRes = R.drawable.image_21, category = "Comedy"))
        movies.add(MovieEntity(title = "21 Jump Street", directorYear = "Phil Lord - 2012", synopsis = "A pair of underachieving cops are sent back to a local high school to blend in and bring down a synthetic drug ring.", rating = "⭐ 4.3", posterRes = R.drawable.image_22, category = "Comedy"))
        movies.add(MovieEntity(title = "Deadpool", directorYear = "Tim Miller - 2016", synopsis = "A wisecracking mercenary gets experimented on and becomes immortal but ugly.", rating = "⭐ 4.7", posterRes = R.drawable.image_23, category = "Comedy"))
        movies.add(MovieEntity(title = "Mean Girls", directorYear = "Mark Waters - 2004", synopsis = "Cady Heron is a hit with The Plastics, the A-list girl clique at her new school, until she makes the mistake of falling for Aaron Samuels.", rating = "⭐ 4.1", posterRes = R.drawable.image_24, category = "Comedy"))
        movies.add(MovieEntity(title = "The Proposal", directorYear = "Anne Fletcher - 2009", synopsis = "A pushy boss forces her young assistant to marry her in order to keep her visa status in the U.S.", rating = "⭐ 4.0", posterRes = R.drawable.image_25, category = "Comedy"))
        movies.add(MovieEntity(title = "Pineapple Express", directorYear = "David Gordon Green - 2008", synopsis = "A process server and his marijuana dealer wind up on the run from British gangsters and a corrupt policewoman.", rating = "⭐ 4.0", posterRes = R.drawable.image_26, category = "Comedy"))
        movies.add(MovieEntity(title = "Easy A", directorYear = "Will Gluck - 2010", synopsis = "A clean-cut high school student relies on the school's rumor mill to advance her social and financial standing.", rating = "⭐ 4.1", posterRes = R.drawable.image_27, category = "Comedy"))

        // Romance
        movies.add(MovieEntity(title = "The Summer I Turned Pretty", directorYear = "Christopher Briney - 2022", synopsis = "Belly Conklin navigates a love triangle with lifelong family friends during a transformative summer.", rating = "⭐ 4.2", posterRes = R.drawable.preety_sum, category = "Romance"))
        movies.add(MovieEntity(title = "My Fault", directorYear = "Nicole Wallace - 2023", synopsis = "Noah has to leave her town and move into the mansion of her mother's new rich husband.", rating = "⭐ 4.3", posterRes = R.drawable.myyfault, category = "Romance"))
        movies.add(MovieEntity(title = "Your Fault", directorYear = "Nicole Wallace - 2024", synopsis = "The love between Noah and Nick seems unbreakable, despite the maneuvers of their parents to separate them.", rating = "⭐ 4.1", posterRes = R.drawable.ffff, category = "Romance"))
        movies.add(MovieEntity(title = "The Notebook", directorYear = "Nick Cassavetes - 2004", synopsis = "A poor yet passionate young man falls in love with a rich young woman, giving her a sense of freedom.", rating = "⭐ 4.7", posterRes = R.drawable.image_28, category = "Romance"))
        movies.add(MovieEntity(title = "About Time", directorYear = "Richard Curtis - 2013", synopsis = "At the age of 21, Tim discovers he can travel in time and change what happens and has happened in his own life.", rating = "⭐ 4.6", posterRes = R.drawable.image_29, category = "Romance"))
        movies.add(MovieEntity(title = "La La Land", directorYear = "Damien Chazelle - 2016", synopsis = "While navigating their careers in Los Angeles, a pianist and an actress fall in love while attempting to reconcile their aspirations.", rating = "⭐ 4.8", posterRes = R.drawable.image_30, category = "Romance"))
        movies.add(MovieEntity(title = "Pride & Prejudice", directorYear = "Joe Wright - 2005", synopsis = "Sparks fly when spirited Elizabeth Bennet meets single, rich, and proud Mr. Darcy.", rating = "⭐ 4.9", posterRes = R.drawable.image_31, category = "Romance"))
        movies.add(MovieEntity(title = "To All the Boys I've Loved Before", directorYear = "Susan Johnson - 2018", synopsis = "A teenage girl's secret love letters are exposed and wreak havoc on her love life.", rating = "⭐ 4.2", posterRes = R.drawable.image_32, category = "Romance"))
        movies.add(MovieEntity(title = "A Star Is Born", directorYear = "Bradley Cooper - 2018", synopsis = "A musician helps a young singer find fame as age and alcoholism send his own career into a downward spiral.", rating = "⭐ 4.5", posterRes = R.drawable.image_33, category = "Romance"))
        movies.add(MovieEntity(title = "Me Before You", directorYear = "Thea Sharrock - 2016", synopsis = "A girl in a small town forms an unlikely bond with a recently-paralyzed man she's taking care of.", rating = "⭐ 4.4", posterRes = R.drawable.image_34, category = "Romance"))

        // Sci-Fi
        movies.add(MovieEntity(title = "Interstellar", directorYear = "Christopher Nolan - 2014", synopsis = "A team of explorers travel through a wormhole in space in an attempt to ensure humanity's survival.", rating = "⭐ 4.9", posterRes = R.drawable.interst, category = "Sci-Fi"))
        movies.add(MovieEntity(title = "Inception", directorYear = "Christopher Nolan - 2010", synopsis = "A thief who steals corporate secrets through dream-sharing technology is given the inverse task.", rating = "⭐ 4.8", posterRes = R.drawable.inceptionn, category = "Sci-Fi"))
        movies.add(MovieEntity(title = "Dune: Part Two", directorYear = "Denis Villeneuve - 2024", synopsis = "Paul Atreides unites with Chani and the Fremen while on a warpath of revenge.", rating = "⭐ 4.9", posterRes = R.drawable.image_35, category = "Sci-Fi"))
        movies.add(MovieEntity(title = "The Martian", directorYear = "Ridley Scott - 2015", synopsis = "An astronaut becomes stranded on Mars after his team assume him dead, and must rely on his ingenuity to find a way to signal to Earth.", rating = "⭐ 4.7", posterRes = R.drawable.image_36, category = "Sci-Fi"))
        movies.add(MovieEntity(title = "Blade Runner 2049", directorYear = "Denis Villeneuve - 2017", synopsis = "A young Blade Runner's discovery of a long-buried secret leads him to track down former Blade Runner Rick Deckard.", rating = "⭐ 4.8", posterRes = R.drawable.image_37, category = "Sci-Fi"))
        movies.add(MovieEntity(title = "Arrival", directorYear = "Denis Villeneuve - 2016", synopsis = "A linguist works with the military to communicate with alien lifeforms after twelve mysterious spacecraft appear around the world.", rating = "⭐ 4.6", posterRes = R.drawable.image_38, category = "Sci-Fi"))
        movies.add(MovieEntity(title = "Tenet", directorYear = "Christopher Nolan - 2020", synopsis = "Armed with only one word, Tenet, and fighting for the survival of the entire world, a Protagonist journeys through a twilight world of international espionage.", rating = "⭐ 4.3", posterRes = R.drawable.image_39, category = "Sci-Fi"))
        movies.add(MovieEntity(title = "Avatar: The Way of Water", directorYear = "James Cameron - 2022", synopsis = "Jake Sully lives with his newfound family formed on the extrasolar moon Pandora.", rating = "⭐ 4.5", posterRes = R.drawable.image_40, category = "Sci-Fi"))
        movies.add(MovieEntity(title = "The Creator", directorYear = "Gareth Edwards - 2023", synopsis = "Against the backdrop of a post-apocalyptic future and a war between the human race and the forces of artificial intelligence.", rating = "⭐ 4.1", posterRes = R.drawable.image_41, category = "Sci-Fi"))
        movies.add(MovieEntity(title = "Gravity", directorYear = "Alfonso Cuarón - 2013", synopsis = "Two astronauts work together to survive after an accident leaves them stranded in space.", rating = "⭐ 4.6", posterRes = R.drawable.image_42, category = "Sci-Fi"))

        // Thriller
        movies.add(MovieEntity(title = "Shutter Island", directorYear = "Martin Scorsese - 2010", synopsis = "Two U.S. marshals are sent to an asylum on a remote island in order to investigate the disappearance of a patient.", rating = "⭐ 4.8", posterRes = R.drawable.image_43, category = "Thriller"))
        movies.add(MovieEntity(title = "Gone Girl", directorYear = "David Fincher - 2014", synopsis = "With his wife's disappearance having become the focus of an intense media circus, a man sees the spotlight turned on him.", rating = "⭐ 4.6", posterRes = R.drawable.image_44, category = "Thriller"))
        movies.add(MovieEntity(title = "Prisoners", directorYear = "Denis Villeneuve - 2013", synopsis = "When Keller Dover's daughter and her friend go missing, he takes matters into his own hands.", rating = "⭐ 4.7", posterRes = R.drawable.image_45, category = "Thriller"))
        movies.add(MovieEntity(title = "The Silence of the Lambs", directorYear = "Jonathan Demme - 1991", synopsis = "A young F.B.I. cadet must receive the help of an incarcerated and manipulative cannibal killer.", rating = "⭐ 4.9", posterRes = R.drawable.image_46, category = "Thriller"))
        movies.add(MovieEntity(title = "Se7en", directorYear = "David Fincher - 1995", synopsis = "Two detectives, a rookie and a veteran, hunt a serial killer who uses the seven deadly sins as his motives.", rating = "⭐ 4.8", posterRes = R.drawable.oth, category = "Thriller"))
        movies.add(MovieEntity(title = "Joker", directorYear = "Todd Phillips - 2019", synopsis = "A mentally troubled stand-up comedian embarks on a downward spiral that leads to the creation of an iconic villain.", rating = "⭐ 4.7", posterRes = R.drawable.oth, category = "Thriller"))
        movies.add(MovieEntity(title = "Parasite", directorYear = "Bong Joon Ho - 2019", synopsis = "Greed and class discrimination threaten a symbiotic relationship between two families.", rating = "⭐ 4.9", posterRes = R.drawable.oth, category = "Thriller"))

        // Drama
        movies.add(MovieEntity(title = "The Whale", directorYear = "Darren Aronofsky - 2022", synopsis = "A reclusive English teacher attempts to reconnect with his estranged teenage daughter.", rating = "⭐ 4.4", posterRes = R.drawable._1bnm4d97wl__sl500_, category = "Drama"))
        movies.add(MovieEntity(title = "Oppenheimer", directorYear = "Christopher Nolan - 2023", synopsis = "The story of American scientist J. Robert Oppenheimer and his role in the development of the atomic bomb.", rating = "⭐ 4.9", posterRes = R.drawable.oth, category = "Drama"))
        movies.add(MovieEntity(title = "The Shawshank Redemption", directorYear = "Frank Darabont - 1994", synopsis = "Over the course of several years, two convicts form a friendship, seeking consolation and, eventually, redemption.", rating = "⭐ 5.0", posterRes = R.drawable.oth, category = "Drama"))
        movies.add(MovieEntity(title = "Forrest Gump", directorYear = "Robert Zemeckis - 1994", synopsis = "The history of the United States from the 1950s to the '70s unfolds from the perspective of an Alabama man with an IQ of 75.", rating = "⭐ 4.8", posterRes = R.drawable.oth, category = "Drama"))
        movies.add(MovieEntity(title = "The Godfather", directorYear = "Francis Ford Coppola - 1972", synopsis = "The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.", rating = "⭐ 5.0", posterRes = R.drawable.the_godfather__the_game, category = "Drama"))
        movies.add(MovieEntity(title = "Schindler's List", directorYear = "Steven Spielberg - 1993", synopsis = "In German-occupied Poland during World War II, industrialist Oskar Schindler gradually becomes concerned for his Jewish workforce.", rating = "⭐ 4.9", posterRes = R.drawable.oth, category = "Drama"))
        movies.add(MovieEntity(title = "Green Book", directorYear = "Peter Farrelly - 2018", synopsis = "A working-class Italian-American bouncer becomes the driver of an African-American classical pianist on a tour through the 1960s American South.", rating = "⭐ 4.7", posterRes = R.drawable.oth, category = "Drama"))
        movies.add(MovieEntity(title = "The Social Network", directorYear = "David Fincher - 2010", synopsis = "As Harvard student Mark Zuckerberg creates the social networking site that would become known as Facebook.", rating = "⭐ 4.6", posterRes = R.drawable.oth, category = "Drama"))
        movies.add(MovieEntity(title = "Whiplash", directorYear = "Damien Chazelle - 2014", synopsis = "A promising young drummer enrolls at a cut-throat music conservatory where his dreams of greatness are mentored by an instructor.", rating = "⭐ 4.8", posterRes = R.drawable.oth, category = "Drama"))

        // Classic
        movies.add(MovieEntity(title = "The Godfather", directorYear = "Francis Ford Coppola - 1972", synopsis = "The aging patriarch of an organized crime dynasty transfers control of his empire to his reluctant son.", rating = "⭐ 5.0", posterRes = R.drawable.the_godfather__the_game, category = "Classic"))
        movies.add(MovieEntity(title = "Citizen Kane", directorYear = "Orson Welles - 1941", synopsis = "Following the death of publishing tycoon Charles Foster Kane, reporters scramble to uncover the meaning of his final word.", rating = "⭐ 4.7", posterRes = R.drawable.oth, category = "Classic"))
        movies.add(MovieEntity(title = "Casablanca", directorYear = "Michael Curtiz - 1942", synopsis = "A cynical American expatriate struggles to decide whether or not he should help his former lover and her fugitive husband escape from French Morocco.", rating = "⭐ 4.8", posterRes = R.drawable.oth, category = "Classic"))
        movies.add(MovieEntity(title = "12 Angry Men", directorYear = "Sidney Lumet - 1957", synopsis = "The jury in a New York City murder trial is frustrated by a single member whose skeptical caution forces them to more carefully consider the evidence.", rating = "⭐ 4.9", posterRes = R.drawable.oth, category = "Classic"))
        movies.add(MovieEntity(title = "Pulp Fiction", directorYear = "Quentin Tarantino - 1994", synopsis = "The lives of two mob hitmen, a boxer, a gangster and his wife, and a pair of diner bandits intertwine in four tales of violence and redemption.", rating = "⭐ 4.9", posterRes = R.drawable.oth, category = "Classic"))
        movies.add(MovieEntity(title = "Singin' in the Rain", directorYear = "Stanley Donen - 1952", synopsis = "A silent film star falls for a chorus girl just as he and his delusionally jealous screen partner are trying to make the difficult transition to talking pictures.", rating = "⭐ 4.7", posterRes = R.drawable.oth, category = "Classic"))
        movies.add(MovieEntity(title = "Psycho", directorYear = "Alfred Hitchcock - 1960", synopsis = "A Phoenix secretary embezzles $40,000 from her employer's client, goes on the run, and checks into a remote motel run by a young man under the domination of his mother.", rating = "⭐ 4.8", posterRes = R.drawable.oth, category = "Classic"))
        movies.add(MovieEntity(title = "Vertigo", directorYear = "Alfred Hitchcock - 1958", synopsis = "A former San Francisco police detective juggles his personal demons and becoming obsessed with the beautiful woman he has been hired to trail.", rating = "⭐ 4.7", posterRes = R.drawable.oth, category = "Classic"))
        movies.add(MovieEntity(title = "Goodfellas", directorYear = "Martin Scorsese - 1990", synopsis = "The story of Henry Hill and his life in the mob, covering his relationship with his wife Karen Hill and his mob partners Jimmy Conway and Tommy DeVito.", rating = "⭐ 4.8", posterRes = R.drawable.oth, category = "Classic"))
        movies.add(MovieEntity(title = "Apocalypse Now", directorYear = "Francis Ford Coppola - 1979", synopsis = "A U.S. Army officer serving in Vietnam is tasked with assassinating a renegade Special Forces Colonel who sees himself as a god.", rating = "⭐ 4.6", posterRes = R.drawable.oth, category = "Classic"))

        // Web Series (Pending Serieses)
        movies.add(MovieEntity(title = "Stranger Things", directorYear = "Duffer Brothers - 2016", synopsis = "When a young boy vanishes, a small town uncovers a mystery involving secret experiments and terrifying supernatural forces.", rating = "⭐ 4.9", posterRes = R.drawable.oth, category = "Web Series"))
        movies.add(MovieEntity(title = "The Boys", directorYear = "Eric Kripke - 2019", synopsis = "A group of vigilantes set out to take down corrupt superheroes who abuse their superpowers.", rating = "⭐ 4.8", posterRes = R.drawable.oth, category = "Web Series"))
        movies.add(MovieEntity(title = "Breaking Bad", directorYear = "Vince Gilligan - 2008", synopsis = "A high school chemistry teacher diagnosed with inoperable lung cancer turns to manufacturing and selling methamphetamine.", rating = "⭐ 5.0", posterRes = R.drawable.oth, category = "Web Series"))
        movies.add(MovieEntity(title = "Money Heist", directorYear = "Álex Pina - 2017", synopsis = "An unusual group of robbers attempt to carry out the most perfect robbery in Spanish history.", rating = "⭐ 4.6", posterRes = R.drawable.oth, category = "Web Series"))
        movies.add(MovieEntity(title = "The Mandalorian", directorYear = "Jon Favreau - 2019", synopsis = "The travels of a lone bounty hunter in the outer reaches of the galaxy, far from the authority of the New Republic.", rating = "⭐ 4.7", posterRes = R.drawable.oth, category = "Web Series"))

        // Add all to DB
        movies.forEach { dao.insert(it) }
    }
}

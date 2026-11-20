package apistandards

import java.text.Normalizer

class Speaker internal constructor(
    val id: String,
    val name: String,
    /**
     * Job title, e.g. "CTO"
     */
    val title: String?,
    val company: String?,
    /**
     * Paragraphs are separated by blank lines
     */
    val bio: String?,
    /**
     * Links to the speaker's profiles, e.g. LinkedIn
     */
    val socials: List<Social>,
    /**
     * Headshot image URL
     */
    val photo: String?,
) {
    /**
     * The sessions this speaker presents, in chronological order
     */
    fun sessions(): List<Session> = allSessions.filter { name in it.speakerNames }
}

/**
 * A link to one of a speaker's profiles
 */
class Social internal constructor(
    /**
     * The network, e.g. "LinkedIn"
     */
    val name: String,
    val url: String,
)

/**
 * "Željko Kozina" -> "zeljko-kozina"
 */
private fun slug(name: String): String =
    Normalizer.normalize(name, Normalizer.Form.NFD)
        .replace(Regex("\\p{M}"), "")
        .lowercase()
        .replace(Regex("[^a-z0-9]+"), "-")
        .trim('-')

private fun speaker(
    name: String,
    title: String? = null,
    company: String? = null,
    socials: List<Social> = emptyList(),
    photo: String? = null,
    bio: String? = null,
) = Speaker(id = slug(name), name = name, title = title, company = company, bio = bio, socials = socials, photo = photo)

/**
 * All speakers, sorted by name. Details come from "API Standards Conference - FOST Paris 2026 - Speakers.csv".
 *
 * Cleanup applied: whitespace trimmed, "TBD" values dropped, LinkedIn URLs normalized,
 * Lukasz Gornicki's job title and company un-swapped, stray "||" removed from Gabriela Colombo's job title.
 * Hugo Guerrero and Ludovic Dussart are not in the CSV.
 *
 * Photos are a best effort, from (in order of preference):
 * - https://api-standards.joinfost.io/ (8 speakers, some of them GitHub avatars linked from that page)
 * - AsyncAPI conference speaker images (Florence Njeri, Thulisile Sibanda, Ignacio Castillejos)
 * - GraphQL Day London 2026 speaker images, https://graphql.org/day/2026/london/ (David Stutt, Ivan Jančić)
 * - GitHub avatars of accounts whose name and company/role match the speaker, checked to be photos
 * GitHub links are accounts whose name and company/role match the speaker, or that the conference sites link to.
 * Matthew Adams's GitHub account (mwadams, Endjin) is matched on name only.
 */
internal val allSpeakers: List<Speaker> = listOf(
    speaker(
        "Annegret Junker",
        title = "Chief Software Architect",
        company = "codecentric AG",
        socials = listOf(
            Social(name = "LinkedIn", url = "https://www.linkedin.com/in/dr-annegret-junker-141a99a4/"),
            Social(name = "GitHub", url = "https://github.com/Grinseteddy"),
        ),
        photo = "https://github.com/Grinseteddy.png?size=400",
    ),
    speaker(
        "Aurelien David",
        title = "Engineering Manager",
        company = "Pennylane",
        socials = listOf(
            Social(name = "LinkedIn", url = "https://www.linkedin.com/in/aurel-spyl/"),
            Social(name = "GitHub", url = "https://github.com/spyl94"),
        ),
        photo = "https://github.com/spyl94.png?size=400",
        bio = """
        Co-founder & CTO heybilly.io acuired by Pennylane - GraphQL foundation ambassador
        """.trimIndent(),
    ),
    speaker(
        "Ayodele Aransiola",
        title = "Solutions Architect",
        company = "Gopaddi",
        socials = listOf(
            Social(name = "LinkedIn", url = "https://www.linkedin.com/in/aransiolaayo/"),
            Social(name = "GitHub", url = "https://github.com/CodeLeom"),
        ),
        photo = "https://github.com/CodeLeom.png?size=400",
        bio = """
        Ayodele is a solution architect and a passionate community builder who shares his wealth of knowledge on architecting scalable products, and software engineering practices.
        """.trimIndent(),
    ),
    speaker(
        "Bart Dubbeldam",
        title = "Founding Engineer",
        company = "Cached Intelligence",
        socials = listOf(Social(name = "GitHub", url = "https://github.com/holodorum")),
        photo = "https://github.com/holodorum.png?size=400",
        bio = """
        I'm Bart, Founding Engineer at Cached Intelligence and a core contributor to KSON. I enjoy solving complex problems across various domains. This has taken me from web development and data analytics to TLS in rustls, git internals in gitoxide and package management in pixi. Away from the keyboard I play chess and used to compete as a professional grappler.
        """.trimIndent(),
    ),
    speaker(
        "Bart Rijksen",
        title = "Data Scientist",
        company = "HEMA",
        socials = listOf(Social(name = "LinkedIn", url = "https://www.linkedin.com/in/bart-rijksen-877785a/")),
        photo = "https://api-standards.joinfost.io/__l5e/assets-v1/14ec9aa2-7af7-4b3a-99ae-64019e3ef67b/bart-rijksen.jpg",
    ),
    speaker(
        "Beda Hammerschmidt",
        title = "VP JSON Development",
        company = "Oracle",
        socials = listOf(Social(name = "LinkedIn", url = "https://www.linkedin.com/in/beda/")),
        photo = "https://api-standards.joinfost.io/__l5e/assets-v1/20431220-b78c-4a8b-9f2e-6d6b7f5d5738/beda-hammerschmidt.jpg",
    ),
    speaker(
        "Ben Hutton",
        title = "Staff Backend Engineer",
        company = "Spoke / JSON Schema TSC",
        socials = listOf(
            Social(name = "LinkedIn", url = "https://www.linkedin.com/in/benhuttonuk/"),
            Social(name = "GitHub", url = "https://github.com/Relequestual"),
        ),
        photo = "https://github.com/Relequestual.png?size=400",
        bio = """
        Most excited by work that enables. Pragmatist. Love to be proved wrong.
        """.trimIndent(),
    ),
    speaker(
        "Camille Lawrence",
        title = "Senior Software Engineer",
        company = "Apollo GraphQL",
        socials = listOf(
            Social(name = "LinkedIn", url = "https://www.linkedin.com/in/camillelawrence/"),
            Social(name = "GitHub", url = "https://github.com/gocamille"),
        ),
        photo = "https://github.com/gocamille.png?size=400",
        bio = """
        Camille Lawrence is a Senior Software Engineer at Apollo GraphQL, where she maintains Apollo MCP Server, the open-source server that turns GraphQL operations into tools for AI agents. She also contributes to the official Rust MCP SDK and is a member of the Agentic AI Foundation's Workflows & Process Integration Working Group. Before Apollo, she was a Staff Software Engineer at CircleCI. She has spent over a decade building developer tools at companies including 1Password, Shopify and Telus.
        """.trimIndent(),
    ),
    speaker(
        "Carles Arnal",
        title = "Principal Software Engineer",
        company = "IBM",
        socials = listOf(
            Social(name = "LinkedIn", url = "https://www.linkedin.com/in/carles-arnal-824b44a5/"),
            Social(name = "GitHub", url = "https://github.com/carlesarnal"),
        ),
        photo = "https://github.com/carlesarnal.png?size=400",
    ),
    speaker(
        "Daniel Kocot",
        title = "Principal Integration Architect",
        company = "adorsys GmbH & Co. KG",
        socials = listOf(
            Social(name = "LinkedIn", url = "https://www.linkedin.com/in/danielkocot/"),
            Social(name = "GitHub", url = "https://github.com/danielkocot"),
        ),
        photo = "https://github.com/danielkocot.png?size=400",
        bio = """
        Daniel Kocot has been part of the adorsys GmbH & Co. KG team as a Principal Integration Architect since January 2026. For nearly ten years, he has been dedicated to topics around integration. He began with a strong focus on gateways and the early days of API operations (APIOps), and later shifted his focus toward methodologies, strategy, and enterprise architecture.
        """.trimIndent(),
    ),
    speaker(
        "David Stutt",
        title = "Senior Founding Engineer",
        company = "Wundergraph",
        socials = listOf(
            Social(name = "LinkedIn", url = "https://www.linkedin.com/in/davidstutt/"),
            Social(name = "GitHub", url = "https://github.com/Aenimus"),
        ),
        photo = "https://graphql.org/_next/static/media/david-stutt.be79067c.jpg",
        bio = """
        David is one of the four founding engineers at WunderGraph who collectively built the software foundations of the company. David's career began in the finance sector of software engineering before moving exclusively to API management in 2022. From that point, David started his deep-dive journey into GraphQL and Federation. David was also the creator of the popular "Will It Compose" game that was presented by Jens Neuse at GraphQL Conf 2023 at the beginning of his Dataloader 3.0 session, which remains the most-watched video on the GraphQL Working Groups YouTube channel. Recently, David gave a talk on "top-down" schema design at GraphQL Conf 2026—Federation, Reversed: A Consumer-First Future with Fission . David is also a member of the composite schema working group committee and tries to provide contributions and input where possible.
        """.trimIndent(),
    ),
    speaker(
        "Erik Wilde",
        title = "Chief Evangelist",
        company = "WunderGraph",
        socials = listOf(Social(name = "GitHub", url = "https://github.com/dret")),
        photo = "https://github.com/dret.png?size=400",
    ),
    speaker(
        "Florence Njeri",
        title = "Cybersecurity Engineer",
        company = "AsyncAPI Initiative",
        socials = listOf(
            Social(name = "LinkedIn", url = "https://www.linkedin.com/in/florencenjeri/"),
            Social(name = "GitHub", url = "https://github.com/Florence-Njeri"),
        ),
        photo = "https://cfp.asyncapi.com/media/avatars/8T9LXD_bZqGDNd.webp",
        bio = """
        Florence Njeri is a Cybersecurity Engineer at the AsyncAPI Initiative where she was part of the incident response for both the Shai-Hulud byTeamPCP and Miasma supply chain attacks, which compromised packages with millions of weekly downloads.
        """.trimIndent(),
    ),
    speaker(
        "Frank Kilcommins",
        title = "Head of Enterprise Architecture",
        company = "Jentic",
        socials = listOf(
            Social(name = "LinkedIn", url = "https://www.linkedin.com/in/frank-kilcommins/"),
            Social(name = "GitHub", url = "https://github.com/frankkilcommins"),
        ),
        photo = "https://api-standards.joinfost.io/__l5e/assets-v1/21eb5cfe-cf64-4cd0-83ef-2cdc98dc144c/frank-kilcommins.jpg",
    ),
    speaker(
        "Gabriela Colombo",
        title = "Software Engineer",
        company = "Swapcard",
        socials = listOf(
            Social(name = "LinkedIn", url = "https://www.linkedin.com/in/gabrielacolomboalveslongo/"),
            Social(name = "GitHub", url = "https://github.com/Gabicolombo"),
        ),
        photo = "https://github.com/Gabicolombo.png?size=400",
        bio = """
        I'm a backend software engineer at Swapcard, an event technology platform used by organizers worldwide. I build distributed services and GraphQL APIs in Node.js and TypeScript, focusing on schema evolution, data consistency, and performance.
        """.trimIndent(),
    ),
    speaker(
        "Galina Tuova",
        title = "Product Manager - Metadata & Domain Alignment CoE",
        company = "SAP",
        socials = listOf(Social(name = "LinkedIn", url = "https://www.linkedin.com/in/galina-tuova-178563157/")),
        photo = "https://media.licdn.com/dms/image/v2/C4E03AQH-6oc-3jefZQ/profile-displayphoto-shrink_200_200/profile-displayphoto-shrink_200_200/0/1604427403885?e=2147483647&v=beta&t=E2K5OEfIODjZ6DWSpdmUSS4TbPtP8Ov7vgG_wfjNV8E",
    ),
    speaker(
        "Hugo Guerrero",
        socials = listOf(Social(name = "GitHub", url = "https://github.com/hguerrero")),
        photo = "https://github.com/hguerrero.png?size=400",
    ),
    speaker(
        "Ignacio Castillejos",
        title = "Staff Engineer",
        company = "The New York Times",
        socials = listOf(Social(name = "LinkedIn", url = "https://www.linkedin.com/in/icast7/")),
        photo = "https://cfp.asyncapi.com/media/avatars/9UY9HT_UEOKfxE.webp",
        bio = """
        Ignacio Castillejos is a Staff Engineer at The New York Times, where he focuses on event-driven architecture, subscription modernization, and building strong engineering cultures. With a career spanning tech giants like Nike and Motorola, Ignacio bridges the gap between deep technical capabilities including extensive expertise in AWS ecosystems and strategic product management.

        He is passionate about process improvement, and developing high-performing, distributed engineering teams. A lifelong learner holding dual master's degrees in Software Engineering and Tech Management, Ignacio loves sharing practical insights on architecture, technical leadership, and scaling global digital platforms.
        """.trimIndent(),
    ),
    speaker(
        "Ivan Jančić",
        title = "Solutions Architect",
        company = "Brighting",
        socials = listOf(Social(name = "LinkedIn", url = "https://www.linkedin.com/in/ivan-jan%C4%8Di%C4%87/")),
        photo = "https://graphql.org/_next/static/media/ivan-jancic.e3010307.jpg",
        bio = """
        I’m a Solutions Architect and backend engineer focused on building scalable cloud systems and modern distributed architectures. My background is centered around cloud, microservices and event-driven systems.

        I hold a Master’s degree in Applied Computer Science and multiple AWS certifications including the AWS Solutions Architect professional. Over the years, I’ve worked across architecture, backend engineering, and technical leadership roles — helping teams design resilient systems that can scale reliably.

        Outside of tech, I enjoy traveling, basketball, discovering good food, and spending time with animals.
        """.trimIndent(),
    ),
    speaker(
        "Jason Desrosiers",
        title = "Director",
        company = "JSON Schema TSC and Hyperjump, LLC",
        socials = listOf(
            Social(name = "LinkedIn", url = "https://www.linkedin.com/in/jason-desrosiers/"),
            Social(name = "GitHub", url = "https://github.com/jdesrosiers"),
        ),
        photo = "https://github.com/jdesrosiers.png?size=400",
        bio = """
        Jason Desrosiers is the creator of Hyperjump, an ecosystem of JSON Schema tools. At its center is @hyperjump/json-schema, a validator and linter for every dialect from draft-04 to JSON Schema v1. Jason has been active in the JSON Schema community for over a decade, from answering questions and maintaining the test suite to developing the specification, and now serves on its Technical Steering Committee (TSC). Jason builds tools that understand what a schema means, and shares the building blocks so others can build their own.
        """.trimIndent(),
    ),
    speaker(
        "Jean-Pierre Dijcks",
        title = "Product Manager",
        company = "Oracle",
        socials = listOf(Social(name = "LinkedIn", url = "https://www.linkedin.com/in/jdijcks/")),
        photo = "https://api-standards.joinfost.io/__l5e/assets-v1/ed2ea4d9-fd6b-41f9-9d5e-3dd6b791e04a/jp-dijcks.jpg",
        bio = """
        JP Dijcks, short for Jean-Pierre, is a Distinguished Product Manager for Oracle Autonomous AI JSON Database and related Oracle JSON technologies. He has spent much of his career helping customers modernize data platforms across data integration, governance, data warehousing, big data, cloud, and AI.
        """.trimIndent(),
    ),
    speaker(
        "Lisa Dusseault",
        title = "CTO",
        company = "Data Transfer Initiative",
        socials = listOf(
            Social(name = "LinkedIn", url = "https://www.linkedin.com/in/lisadusseault/"),
            Social(name = "GitHub", url = "https://github.com/lisad"),
        ),
        photo = "https://github.com/lisad.png?size=400",
        bio = """
        Lisa Dusseault is the CTO of the non-profit Data Transfer Initiative, supporting consumer data portability across tech platforms. A dual career in standards and startups has included being founder, CTO or VP Eng of several tech startups, and several standards writing/organizing credits such as co-author of CalDAV, editor of WebDAV, chair of the XMPP and IMAPExt working groups, and Area Director for Applications area work at the IETF. Today she's focused on JSON Schema standards and how to get the AI industry to support secure personal data portability.
        """.trimIndent(),
    ),
    speaker(
        "Ludovic Dussart",
        socials = listOf(Social(name = "GitHub", url = "https://github.com/M3lkior")),
        photo = "https://github.com/M3lkior.png?size=400",
    ),
    speaker(
        "Lukasz Gornicki",
        title = "Open Source Maintainer",
        company = "Brain Fart Services",
        socials = listOf(
            Social(name = "LinkedIn", url = "https://www.linkedin.com/in/lukasz-gornicki-a621914/"),
            Social(name = "GitHub", url = "https://github.com/derberg"),
        ),
        photo = "https://github.com/derberg.png?size=400",
    ),
    speaker(
        "Matthew Adams",
        socials = listOf(Social(name = "GitHub", url = "https://github.com/mwadams")),
        photo = "https://github.com/mwadams.png?size=400",
    ),
    speaker(
        "Miguel Quintero",
        company = "Adapt",
        socials = listOf(Social(name = "GitHub", url = "https://github.com/miqui")),
    ),
    speaker(
        "Naresh Jain",
        title = "Founder",
        company = "Specmatic",
        socials = listOf(
            Social(name = "LinkedIn", url = "https://www.linkedin.com/in/nareshjain/"),
            Social(name = "GitHub", url = "https://github.com/nashjain"),
        ),
        photo = "https://github.com/nashjain.png?size=400",
    ),
    speaker(
        "Pascal Senn",
        title = "COO",
        company = "Chillicream",
        socials = listOf(
            Social(name = "LinkedIn", url = "https://www.linkedin.com/in/pascal-senn-90899a15a/"),
            Social(name = "GitHub", url = "https://github.com/PascalSenn"),
        ),
        photo = "https://github.com/PascalSenn.png?size=400",
        bio = """
        I'm co-founder of ChilliCream, where we're passionate about advancing the GraphQL ecosystem. We develop and maintain open-source software, actively help and participate in the community, and create tools that help developers to get the most out of their GraphQL APIs. Since 2025, I’ve also been a member of the GraphQL Technical Steering Committee.
        """.trimIndent(),
    ),
    speaker(
        "Pubudu Gunatilaka",
        title = "Director & Head of Architecture, API Platform BU",
        company = "Choreo Labs",
        socials = listOf(
            Social(name = "LinkedIn", url = "https://www.linkedin.com/in/pubudugunatilaka/"),
            Social(name = "GitHub", url = "https://github.com/pubudu538"),
        ),
        photo = "https://github.com/pubudu538.png?size=400",
    ),
    speaker(
        "Reuven Harrison",
        company = "oasdiff",
        socials = listOf(Social(name = "GitHub", url = "https://github.com/reuvenharrison")),
        photo = "https://github.com/reuvenharrison.png?size=400",
    ),
    speaker(
        "Simon Harrer",
        title = "CEO",
        company = "Entropy Data",
        socials = listOf(
            Social(name = "LinkedIn", url = "https://www.linkedin.com/in/simonharrer/"),
            Social(name = "GitHub", url = "https://github.com/simonharrer"),
        ),
        photo = "https://github.com/simonharrer.png?size=400",
    ),
    speaker(
        "Thulisile Sibanda",
        title = "Senior Community Manager",
        company = "AsyncAPI Initiative",
        socials = listOf(
            Social(name = "LinkedIn", url = "https://www.linkedin.com/in/v-thulisile-sibanda/"),
            Social(name = "GitHub", url = "https://github.com/thulieblack"),
        ),
        photo = "https://cfp.asyncapi.com/media/avatars/7ZR7AK_PGvVKqD.webp",
        bio = """
        I build and support Open Source programs and communities, the kind where contributors feel welcome, documentation makes sense, and people want to stick around.

        I wear many hats: Maintainer, Contributor, Program Manager, Documentation Writer, Community Advocate, and occasional data person. What ties it all together? Helping individuals do their best work in Open Source.
        """.trimIndent(),
    ),
    speaker(
        "Željko Kozina",
        title = "Technical Architect",
        company = "Chronomill",
        socials = listOf(
            Social(name = "LinkedIn", url = "https://www.linkedin.com/in/zkozina/"),
            Social(name = "GitHub", url = "https://github.com/zkozina"),
        ),
        photo = "https://github.com/zkozina.png?size=400",
        bio = """
        Željko has spent over 15 years in IT, mostly consulting for banks and insurance companies, with a focus on modernising legacy systems. Along the way, he developed deep expertise in JVM-based technologies and has become a go-to voice on topics such as API design. When he's not untangling someone else's technical debt, he's happily creating his own through a growing collection of personal projects he enthusiastically starts and occasionally finishes. Željko is passionate about sharing knowledge in a way that actually sticks, which usually means keeping things practical, a little entertaining, and free of unnecessary jargon.
        """.trimIndent(),
    ),
).also { speakers ->
    val unknown = allSessions.flatMap { it.speakerNames }.toSet() - speakers.map { it.name }.toSet()
    check(unknown.isEmpty()) { "Sessions reference unknown speakers: $unknown" }
}

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
     * LinkedIn profile URL
     */
    val social: String?,
) {
    /**
     * The sessions this speaker presents, in chronological order
     */
    fun sessions(): List<Session> = allSessions.filter { name in it.speakerNames }
}

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
    social: String? = null,
    bio: String? = null,
) = Speaker(id = slug(name), name = name, title = title, company = company, bio = bio, social = social)

/**
 * All speakers, sorted by name. Details come from "API Standards Conference - FOST Paris 2026 - Speakers.csv".
 *
 * Cleanup applied: whitespace trimmed, "TBD" values dropped, LinkedIn URLs normalized,
 * Lukasz Gornicki's job title and company un-swapped, stray "||" removed from Gabriela Colombo's job title.
 * Hugo Guerrero and Ludovic Dussart are not in the CSV.
 */
internal val allSpeakers: List<Speaker> = listOf(
    speaker(
        "Annegret Junker",
        title = "Chief Software Architect",
        company = "codecentric AG",
        social = "https://www.linkedin.com/in/dr-annegret-junker-141a99a4/",
    ),
    speaker(
        "Aurelien David",
        title = "Engineering Manager",
        company = "Pennylane",
        social = "https://www.linkedin.com/in/aurel-spyl/",
        bio = """
        Co-founder & CTO heybilly.io acuired by Pennylane - GraphQL foundation ambassador
        """.trimIndent(),
    ),
    speaker(
        "Ayodele Aransiola",
        title = "Solutions Architect",
        company = "Gopaddi",
        social = "https://www.linkedin.com/in/aransiolaayo/",
        bio = """
        Ayodele is a solution architect and a passionate community builder who shares his wealth of knowledge on architecting scalable products, and software engineering practices.
        """.trimIndent(),
    ),
    speaker(
        "Bart Dubbeldam",
        title = "Founding Engineer",
        company = "Cached Intelligence",
        bio = """
        I'm Bart, Founding Engineer at Cached Intelligence and a core contributor to KSON. I enjoy solving complex problems across various domains. This has taken me from web development and data analytics to TLS in rustls, git internals in gitoxide and package management in pixi. Away from the keyboard I play chess and used to compete as a professional grappler.
        """.trimIndent(),
    ),
    speaker("Bart Rijksen", title = "Data Scientist", company = "HEMA", social = "https://www.linkedin.com/in/bart-rijksen-877785a/"),
    speaker("Beda Hammerschmidt", title = "VP JSON Development", company = "Oracle", social = "https://www.linkedin.com/in/beda/"),
    speaker(
        "Ben Hutton",
        title = "Staff Backend Engineer",
        company = "Spoke / JSON Schema TSC",
        social = "https://www.linkedin.com/in/benhuttonuk/",
        bio = """
        Most excited by work that enables. Pragmatist. Love to be proved wrong.
        """.trimIndent(),
    ),
    speaker(
        "Camille Lawrence",
        title = "Senior Software Engineer",
        company = "Apollo GraphQL",
        social = "https://www.linkedin.com/in/camillelawrence/",
        bio = """
        Camille Lawrence is a Senior Software Engineer at Apollo GraphQL, where she maintains Apollo MCP Server, the open-source server that turns GraphQL operations into tools for AI agents. She also contributes to the official Rust MCP SDK and is a member of the Agentic AI Foundation's Workflows & Process Integration Working Group. Before Apollo, she was a Staff Software Engineer at CircleCI. She has spent over a decade building developer tools at companies including 1Password, Shopify and Telus.
        """.trimIndent(),
    ),
    speaker(
        "Carles Arnal",
        title = "Principal Software Engineer",
        company = "IBM",
        social = "https://www.linkedin.com/in/carles-arnal-824b44a5/",
    ),
    speaker(
        "Daniel Kocot",
        title = "Principal Integration Architect",
        company = "adorsys GmbH & Co. KG",
        social = "https://www.linkedin.com/in/danielkocot/",
        bio = """
        Daniel Kocot has been part of the adorsys GmbH & Co. KG team as a Principal Integration Architect since January 2026. For nearly ten years, he has been dedicated to topics around integration. He began with a strong focus on gateways and the early days of API operations (APIOps), and later shifted his focus toward methodologies, strategy, and enterprise architecture.
        """.trimIndent(),
    ),
    speaker(
        "David Stutt",
        title = "Senior Founding Engineer",
        company = "Wundergraph",
        social = "https://www.linkedin.com/in/davidstutt/",
        bio = """
        David is one of the four founding engineers at WunderGraph who collectively built the software foundations of the company. David's career began in the finance sector of software engineering before moving exclusively to API management in 2022. From that point, David started his deep-dive journey into GraphQL and Federation. David was also the creator of the popular "Will It Compose" game that was presented by Jens Neuse at GraphQL Conf 2023 at the beginning of his Dataloader 3.0 session, which remains the most-watched video on the GraphQL Working Groups YouTube channel. Recently, David gave a talk on "top-down" schema design at GraphQL Conf 2026—Federation, Reversed: A Consumer-First Future with Fission . David is also a member of the composite schema working group committee and tries to provide contributions and input where possible.
        """.trimIndent(),
    ),
    speaker("Erik Wilde", title = "Chief Evangelist", company = "WunderGraph"),
    speaker(
        "Florence Njeri",
        title = "Cybersecurity Engineer",
        company = "AsyncAPI Initiative",
        social = "https://www.linkedin.com/in/florencenjeri/",
        bio = """
        Florence Njeri is a Cybersecurity Engineer at the AsyncAPI Initiative where she was part of the incident response for both the Shai-Hulud byTeamPCP and Miasma supply chain attacks, which compromised packages with millions of weekly downloads.
        """.trimIndent(),
    ),
    speaker(
        "Frank Kilcommins",
        title = "Head of Enterprise Architecture",
        company = "Jentic",
        social = "https://www.linkedin.com/in/frank-kilcommins/",
    ),
    speaker(
        "Gabriela Colombo",
        title = "Software Engineer",
        company = "Swapcard",
        social = "https://www.linkedin.com/in/gabrielacolomboalveslongo/",
        bio = """
        I'm a backend software engineer at Swapcard, an event technology platform used by organizers worldwide. I build distributed services and GraphQL APIs in Node.js and TypeScript, focusing on schema evolution, data consistency, and performance.
        """.trimIndent(),
    ),
    speaker(
        "Galina Tuova",
        title = "Product Manager - Metadata & Domain Alignment CoE",
        company = "SAP",
        social = "https://www.linkedin.com/in/galina-tuova-178563157/",
    ),
    speaker("Hugo Guerrero"),
    speaker(
        "Ignacio Castillejos",
        title = "Staff Engineer",
        company = "The New York Times",
        social = "https://www.linkedin.com/in/icast7/",
        bio = """
        Ignacio Castillejos is a Staff Engineer at The New York Times, where he focuses on event-driven architecture, subscription modernization, and building strong engineering cultures. With a career spanning tech giants like Nike and Motorola, Ignacio bridges the gap between deep technical capabilities including extensive expertise in AWS ecosystems and strategic product management.

        He is passionate about process improvement, and developing high-performing, distributed engineering teams. A lifelong learner holding dual master's degrees in Software Engineering and Tech Management, Ignacio loves sharing practical insights on architecture, technical leadership, and scaling global digital platforms.
        """.trimIndent(),
    ),
    speaker(
        "Ivan Jančić",
        title = "Solutions Architect",
        company = "Brighting",
        social = "https://www.linkedin.com/in/ivan-jan%C4%8Di%C4%87/",
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
        social = "https://www.linkedin.com/in/jason-desrosiers/",
        bio = """
        Jason Desrosiers is the creator of Hyperjump, an ecosystem of JSON Schema tools. At its center is @hyperjump/json-schema, a validator and linter for every dialect from draft-04 to JSON Schema v1. Jason has been active in the JSON Schema community for over a decade, from answering questions and maintaining the test suite to developing the specification, and now serves on its Technical Steering Committee (TSC). Jason builds tools that understand what a schema means, and shares the building blocks so others can build their own.
        """.trimIndent(),
    ),
    speaker(
        "Jean-Pierre Dijcks",
        title = "Product Manager",
        company = "Oracle",
        social = "https://www.linkedin.com/in/jdijcks/",
        bio = """
        JP Dijcks, short for Jean-Pierre, is a Distinguished Product Manager for Oracle Autonomous AI JSON Database and related Oracle JSON technologies. He has spent much of his career helping customers modernize data platforms across data integration, governance, data warehousing, big data, cloud, and AI.
        """.trimIndent(),
    ),
    speaker(
        "Lisa Dusseault",
        title = "CTO",
        company = "Data Transfer Initiative",
        social = "https://www.linkedin.com/in/lisadusseault/",
        bio = """
        Lisa Dusseault is the CTO of the non-profit Data Transfer Initiative, supporting consumer data portability across tech platforms. A dual career in standards and startups has included being founder, CTO or VP Eng of several tech startups, and several standards writing/organizing credits such as co-author of CalDAV, editor of WebDAV, chair of the XMPP and IMAPExt working groups, and Area Director for Applications area work at the IETF. Today she's focused on JSON Schema standards and how to get the AI industry to support secure personal data portability.
        """.trimIndent(),
    ),
    speaker("Ludovic Dussart"),
    speaker(
        "Lukasz Gornicki",
        title = "Open Source Maintainer",
        company = "Brain Fart Services",
        social = "https://www.linkedin.com/in/lukasz-gornicki-a621914/",
    ),
    speaker("Matthew Adams"),
    speaker("Miguel Quintero", company = "Adapt"),
    speaker("Naresh Jain", title = "Founder", company = "Specmatic", social = "https://www.linkedin.com/in/nareshjain/"),
    speaker(
        "Pascal Senn",
        title = "COO",
        company = "Chillicream",
        social = "https://www.linkedin.com/in/pascal-senn-90899a15a/",
        bio = """
        I'm co-founder of ChilliCream, where we're passionate about advancing the GraphQL ecosystem. We develop and maintain open-source software, actively help and participate in the community, and create tools that help developers to get the most out of their GraphQL APIs. Since 2025, I’ve also been a member of the GraphQL Technical Steering Committee.
        """.trimIndent(),
    ),
    speaker(
        "Pubudu Gunatilaka",
        title = "Director & Head of Architecture, API Platform BU",
        company = "WSO2",
        social = "https://www.linkedin.com/in/pubudugunatilaka/",
    ),
    speaker("Reuven Harrison", company = "oasdiff"),
    speaker("Simon Harrer", title = "CEO", company = "Entropy Data", social = "https://www.linkedin.com/in/simonharrer/"),
    speaker(
        "Thulisile Sibanda",
        title = "Senior Community Manager",
        company = "AsyncAPI Initiative",
        social = "https://www.linkedin.com/in/v-thulisile-sibanda/",
        bio = """
        I build and support Open Source programs and communities, the kind where contributors feel welcome, documentation makes sense, and people want to stick around.

        I wear many hats: Maintainer, Contributor, Program Manager, Documentation Writer, Community Advocate, and occasional data person. What ties it all together? Helping individuals do their best work in Open Source.
        """.trimIndent(),
    ),
    speaker(
        "Željko Kozina",
        title = "Technical Architect",
        company = "Chronomill",
        social = "https://www.linkedin.com/in/zkozina/",
        bio = """
        Željko has spent over 15 years in IT, mostly consulting for banks and insurance companies, with a focus on modernising legacy systems. Along the way, he developed deep expertise in JVM-based technologies and has become a go-to voice on topics such as API design. When he's not untangling someone else's technical debt, he's happily creating his own through a growing collection of personal projects he enthusiastically starts and occasionally finishes. Željko is passionate about sharing knowledge in a way that actually sticks, which usually means keeping things practical, a little entertaining, and free of unnecessary jargon.
        """.trimIndent(),
    ),
).also { speakers ->
    val unknown = allSessions.flatMap { it.speakerNames }.toSet() - speakers.map { it.name }.toSet()
    check(unknown.isEmpty()) { "Sessions reference unknown speakers: $unknown" }
}

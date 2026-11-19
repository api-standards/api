package apistandards

/**
 * A time slot on the agenda: either a session or a service slot such as a break or lunch
 */
sealed interface Slot {
    val id: String

    /**
     * The day number: 1, 2 or 3
     */
    val day: Int

    /**
     * Start time in Paris local time (CET, UTC+01:00), e.g. "2026-12-01T11:00:00"
     */
    val startsAt: LocalDateTime

    /**
     * End time in Paris local time (CET, UTC+01:00), e.g. "2026-12-01T11:25:00"
     */
    val endsAt: LocalDateTime
}

/**
 * A talk, panel or other agenda slot that has a title
 */
class Session internal constructor(
    override val id: String,
    val title: String,
    /**
     * The talk abstract, or null if none was provided. Paragraphs are separated by blank lines.
     */
    val description: String?,
    override val day: Int,
    override val startsAt: LocalDateTime,
    override val endsAt: LocalDateTime,
    internal val speakerNames: List<String>,
) : Slot {
    fun speakers(): List<Speaker> = speakerNames.map { name -> allSpeakers.first { it.name == name } }
}

/**
 * A non-session slot on the agenda, such as a coffee break or lunch
 */
class ServiceSlot internal constructor(
    override val id: String,
    val title: String,
    override val day: Int,
    override val startsAt: LocalDateTime,
    override val endsAt: LocalDateTime,
) : Slot

/*
 * The agenda data, transcribed from the
 * "API Standards Conference - FOST Paris 2026 - Agenda Day {1,2,3}.csv" exports.
 *
 * "End of Day" rows are left out. These source typos were fixed:
 * - Day 1 "12:00 PM - 12:25" (missing PM on the end time)
 * - Day 2 "12:20 AM - 12:45 PM" -> 12:20 PM
 * - Day 3 "11:30 PM - 11:55 PM" -> 11:30 AM
 * - Day 2 "Jean-Pierre DIjcks" -> "Jean-Pierre Dijcks"; "&" in the speaker cell is treated as two speakers
 * - Day 3 "Unconference / API standards 2027 working group" is kept as a session without speakers
 */
private val dates = mapOf(1 to "2026-12-01", 2 to "2026-12-02", 3 to "2026-12-03")

// Times are Paris local time (CET, UTC+01:00 in December)
private fun timestamp(day: Int, time: String): LocalDateTime =
    LocalDateTime.parse("${dates.getValue(day)}T$time:00")

private fun id(day: Int, start: String) = "day$day-${start.replace(":", "")}"

private fun session(
    day: Int,
    start: String,
    end: String,
    title: String,
    speakers: List<String> = emptyList(),
    description: String? = null,
) = Session(
    id = id(day, start),
    title = title,
    description = description,
    day = day,
    startsAt = timestamp(day, start),
    endsAt = timestamp(day, end),
    speakerNames = speakers,
)

private fun service(day: Int, start: String, end: String, title: String) = ServiceSlot(
    id = id(day, start),
    title = title,
    day = day,
    startsAt = timestamp(day, start),
    endsAt = timestamp(day, end),
)

/**
 * The whole agenda in chronological order.
 *
 * Breaks and lunches are inferred from the 15+ minute gaps between sessions:
 * lunch is the gap ending at 14:00, the others are breaks.
 */
internal val allSlots: List<Slot> = listOf(
    // Day 1: December 1
    session(
        day = 1,
        start = "11:00",
        end = "11:25",
        title = "API Standards Conference Welcome and OpenAPI in the Age of AI",
        speakers = listOf("Erik Wilde"),
        description = """
        OpenAPI has seen a lot of success since it was created as Swagger in 2010 and became OpenAPI in 2016. What is the reason for its continued momentum and how does the landscape change with AI entering the picture in a variety of ways? We look at how OpenAPI can help you, how OAI can help you using OpenAPI better, and how AI means that you have to slightly adjust the way you design and manage your APIs.
        """.trimIndent(),
    ),
    session(
        day = 1,
        start = "11:30",
        end = "11:55",
        title = "GraphQL Variables Meet JSON Schema: The New Pitfall When AI Agents Call Your API",
        speakers = listOf("Camille Lawrence"),
    ),
    session(
        day = 1,
        start = "12:00",
        end = "12:25",
        title = "Securing Open Source CI/CD Pipelines: Lessons from Handling 2 Supply Chain Incidents",
        speakers = listOf("Florence Njeri", "Lukasz Gornicki"),
        description = """
        Open-source packages are now the primary attack surface used by threat actors. Rather than targeting enterprises directly, attackers are now compromising widely-used packages with a goal of reaching millions of CI/CD systems simultaneously through trusted releases.

        Because these open-source packages form the backbone of modern enterprise workflows, including cloud pipelines, CI/CD systems, and developer tooling, attackers are increasingly targeting open-source organizations to poison these packages and publish them to trusted registries such as npm and the VS Code Marketplace, compromising hundreds of downstream organizations simultaneously through trusted releases installed by their developers.

        Over the past year, AsyncAPI faced two supply chain attacks by **TeamPCP** and **Miasma**, compromising packages with up to 4M+ combined weekly downloads, such as:

        - @asyncapi/specs (4M+ weekly)
        - @asyncapi/generator (~130k weekly)
        - Official VS Code extensions

        This session presents the tactics, techniques, and procedures (TTPs) we saw used in both incidents. We will trace how a single compromised release propagates into enterprise and public sector environments across Europe and France.

        Finally, attendees will gain actionable defense strategies and tools we have been using to tighten our security measures. We will showcase how we overhauled our release pipelines using:

        - OIDC Trusted Publishing
        - Automated CI/CD security scanning of every workflow change before it can release
        - Hardened token isolation
        - SHA-pinned GitHub Actions
        - Least-privilege permission scopes on automation bots

        Attendees will leave with practical resources and tools to audit their own GitHub Actions, build pipelines, and IDE extension supply chains against active threat groups.

        **The Technical Incidents:**

        This section of the talk will provides first-hand incident response account from the AsyncAPI Initiative security team following two real-world software supply-chain compromises orchestrated by threat groups TeamPCP and Miasma.

        - Exploitation of GitHub Actions pull_request_target workflows (pwn requests) to steal elevated repository secrets.
        - Spam pull requests used to hide the real attack in the workflow logs, and a docs PR using an MDX file to run attacker code in our build pipeline.
        - How the attackers released compromised packages with trusted credentials releasing malicious builds of @asyncapi/specs, @asyncapi/generator, and developer VS Code extensions directly to npm and the VS Code Marketplace, including one release through our own OIDC pipeline.

        **Direct Impact of Supply Chain Attacks for Financial Sector Attendees in France and the EU:**

        For banking, fintech, and public sector ecosystems in France and the EU operating under strict EU regulatory frameworks like DORA and NIS2, open-source packages and developer extensions represent an unmonitored third-party dependency vector. A poisoned upstream library imported into an internal financial pipeline or a developer’s IDE circumvents traditional perimeter controls.

        **Actionable Takeaways:**

        Attendees will leave with practical blueprints we implemented to remediate and harden our release infrastructure and further insights on how to protect their organizations from being compromised through supply chain attacks.
        """.trimIndent(),
    ),
    session(
        day = 1,
        start = "12:30",
        end = "12:55",
        title = "Make Your API Metadata AI-Ready and Automatically Discoverable by Agents",
        speakers = listOf("Galina Tuova"),
        description = """
        Open Resource Discovery (ORD) addresses this with an open standard — governed by the Linux Foundation (NeoNephos Project) — for decentralized, self-describing systems. Instead of registering everything in a central catalog by hand, systems publish machine-readable metadata at the source, so catalogs, tools, and AI agents can crawl and refresh it automatically from a single entry point. ORD complements OpenAPI and AsyncAPI rather than replacing them, adding the high-level context and relationships that make APIs — along with events, data products, and AI resources such as agents, skills, and workflows — automatically discoverable, together with their contracts and documentation.

        ORD is also built for AI consumers: dedicated AI-facing metadata (such as hints for LLMs and agent orchestrators) and native support for agent protocols let AI orchestrators dynamically discover the capabilities available to them in a system landscape.

        With ORD, organizations can publish their metadata offerings, analyze their IT landscape in real time, and build platforms for software development, data management, and AI.
        """.trimIndent(),
    ),
    service(day = 1, start = "12:55", end = "14:00", title = "Lunch"),
    session(
        day = 1,
        start = "14:00",
        end = "14:25",
        title = "Beyond Validation: Reasoning About What a Schema Means",
        speakers = listOf("Jason Desrosiers"),
        description = """
        Most JSON Schema implementations evaluate keywords. A validator checks a value against "minimum" or "properties" and reports whether it passed. That's enough for validation, but other questions need more. Does this schema allow anything at all? Does this "minimum" contradict that "maximum"? Is this required property one that "additionalProperties: false" forbids? Answering those means reasoning about what a schema means, not just evaluating it.

        Version 2 of @hyperjump/json-schema is built around that idea. Every keyword, in every dialect from draft-04 to JSON Schema v1, lowers to a kernel: a small language of operations with a normal form. The validator runs the kernel, the optimizer rewrites it, and the output formats explain it. The linter's 48 rules read it to answer questions like the ones above. None of them keeps its own facts about keywords. What a keyword does comes from what it lowers to, so a rule written once works in every dialect. That includes custom keywords. A custom keyword is defined by what it lowers to, so the linter and optimizer understand it like a built-in one, with no extra code.

        Understanding a schema also makes it fast. On the jsonschema-benchmark corpus, v2 validates 14 times faster than v1 and about 4 times faster than Ajv. It does that without generating code: no "eval" or "new Function", so it runs under a strict Content Security Policy.

        This talk shows what lowering looks like and how it makes dialects comparable. It covers how the optimizer gets speed without code generation, and how lint rules work, down to the kernel expressions they read. Attendees will leave with a way of thinking about schema semantics they can use in their own implementations and tools.
        """.trimIndent(),
    ),
    session(
        day = 1,
        start = "14:30",
        end = "14:55",
        title = "One to many without breaking anyone: evolving a GraphQL schema with many consumers",
        speakers = listOf("Gabriela Colombo"),
    ),
    session(
        day = 1,
        start = "15:00",
        end = "15:25",
        title = "Beyond the Broker: What Event Gateways Are Really Trying to Solve",
        speakers = listOf("Daniel Kocot"),
        description = """
        Event gateways are becoming a popular answer to a real problem in event-driven architecture: the lack of a clear control plane.
        But they should not be understood as API gateways for events. Events have different failure modes. Ownership is less visible. Compatibility changes over time. Policies are harder to enforce without coupling producers and consumers again. Observability depends on more than traffic flowing through a single point.
        This talk examines where event gateways are useful, where brokers, schema registries, catalogs, and portals already cover part of the problem, and where the gateway metaphor starts to break down.
        The focus is not another infrastructure layer. The focus is which governance capabilities event-driven systems actually need, and how AsyncAPI can help describe them.
        """.trimIndent(),
    ),
    session(
        day = 1,
        start = "15:30",
        end = "15:55",
        title = "Arazzo: Turning Business Workflows into Agent Capabilities via MCP and Beyond",
        speakers = listOf("Frank Kilcommins"),
        description = """
        Giving agents raw API operations is like handing someone a parts list instead of a finished product. Individually the operations are valid but assembled in the wrong order, with the wrong inputs, they produce the wrong outcomes. This is wasteful, unpredictable, and incurs a real cost on both sides of the interaction.

        Arazzo changes the unit of exchange. By composing API operations into business-defined workflows with typed inputs, success criteria and structured failure handling, Arazzo lets organisations expose what agents actually need: goal-oriented capabilities, not a catalogue of primitives. Those workflows become first-class MCP tools — derived directly from the Arazzo document, with no hard-coded tool definitions to write or lifecycle to manage. Descriptions, typed schemas and examples are generated from the workflow itself, reducing search-and-execute overhead and guiding agents toward completion rather than exploration.

        The difference is measurable. This talk walks through how workflow-backed MCP tools affect task completion rates, token efficiency and predictable execution cost compared to operation-by-operation exposure, and in a manner that can be verified across models, so the improvements hold regardless of which LLM is doing the work. Evals become more possible which turns "it worked in testing" into "we know it works at the boundary, consistently and within budget."

        And as Arazzo is an open specification it makes those improvements portable and vendor-independent, turning enterprise workflow knowledge into durable, auditable infrastructure for the agentic era.
        """.trimIndent(),
    ),
    service(day = 1, start = "15:55", end = "16:30", title = "Break"),
    session(
        day = 1,
        start = "16:30",
        end = "16:55",
        title = "Not Yet Valid: Four Questions Your Editor Asks a JSON Schema",
        speakers = listOf("Bart Dubbeldam"),
        description = """
        A schema answers the question: is this finished document valid? However, an editor often sees an unfinished document. While someone types, the file is not yet valid, and the editor still has to answer four questions:

        1. Where am I in a half-typed, broken document?
        2. What could go here?
        3. What actually went wrong?
        4. What does this part of the schema mean?

        This talk works through each question using the JSON Schema support behind hover, completion, go-to-definition and diagnostics in KSON, an open-source configuration language.

        Some examples: an `if` that depends on a field you haven't typed yet. A single typo in a `oneOf` that gives you an error for every branch, even though `const` and `enum` could tell the editor which branch you meant. A property with three descriptions, and no rule for which one hover shows.

        Each section ends with a rule schema authors can apply today.
        """.trimIndent(),
    ),
    session(
        day = 1,
        start = "17:00",
        end = "17:25",
        title = "oasdiff vs Claude: A Case Study in LLMs vs Dedicated Tools",
        speakers = listOf("Reuven Harrison"),
        description = """
        Frontier models can now judge a single OpenAPI change correctly almost every time. Will general LLMs replace dedicated tools? We took oasdiff, an open-source OpenAPI breaking-change checker, as a test case and ran the experiment. Three Claude models faced oasdiff on six spec changes, five of them from real bug reports. The best model was right on all five cases the checker had missed. The smaller ones changed their verdict on identical input or applied the wrong rule with confidence. On the 44 MB Microsoft Graph spec, far beyond any context window, agents matched oasdiff's 521 affected operations only by writing a one-off program for that one kind of change. And when Claude fixed one of the bugs it had found, it introduced a new one that only review caught. The results fit a maturity model for turning intelligence into a durable capability: ask the model for each answer, have it write a throwaway program, have it develop or enhance a maintained tool under review where every failure becomes a test, then call it only for cases the tool cannot decide. Each step trades breadth for what a CI gate needs: every change covered, the same answer every run, stable findings, explicit policy, near-zero cost. Climbing these modes is the maintainers' job, not the users'. A shared tool also pools what every team learns, so one team's edge case becomes everyone's rule. Better models make such tools cheaper to build: expect more of them, not fewer.
        """.trimIndent(),
    ),

    // Day 2: December 2
    session(
        day = 2,
        start = "11:20",
        end = "11:45",
        title = "Explaining GraphQL Federation to my non-technical wife",
        speakers = listOf("David Stutt"),
    ),
    session(
        day = 2,
        start = "11:50",
        end = "12:15",
        title = "JSON Schema Standardization and Versions",
        speakers = listOf("Lisa Dusseault"),
        description = """
        One of the most important tools to AI and APIs is now the subject matter of an active standardization Working Group at the IETF. Yet there's no reason to cut off innovation and extensions. How is this going to work? Who can contribute? (hint: everyone). Get informed, maybe even get involved.
        """.trimIndent(),
    ),
    session(
        day = 2,
        start = "12:20",
        end = "12:45",
        title = "Events as Contracts, Events as Data: Our AsyncAPI Journey",
        speakers = listOf("Ignacio Castillejos"),
        description = """
        Migrating an event-driven architecture isn't just a change of broker. It means renegotiating contracts between every producer and consumer. In this talk, we share our journey from a legacy EDA built on GCP Pub/Sub and Avro to one built on AWS EventBridge, where every event follows the CloudEvents standard and the entire event API is defined by an AsyncAPI spec. We'll also show how those same events now power our data products.

        We'll cover:

        Why we changed. Routing messages on our legacy stack was hard, the setup carried too much infrastructure to maintain, and Avro depended on a schema registry we didn't control.
        Contract-first events with AsyncAPI. We govern our event schemas contract-first, the same way we govern our REST APIs with OpenAPI, with the spec as the source of truth. We'll show how that replaced the external schema registry and what the workflow looks like day to day.
        From events to data products. Because every event has a well-defined contract, the same events that drive our services land in our data lakehouse, where they're transformed into data marts and continuously monitored for quality.

        Attendees will leave with practical lessons on using AsyncAPI to make events a reliable foundation for both services and data products.
        """.trimIndent(),
    ),
    service(day = 2, start = "12:45", end = "14:00", title = "Lunch"),
    session(
        day = 2,
        start = "14:00",
        end = "14:25",
        title = "Your Portal Has Two Kinds of Visitors Now: Arazzo Workflows for Humans and Agents",
        speakers = listOf("Pubudu Gunatilaka"),
        description = """
        OpenAPI tells an agent which operations exist. It does not tell it that placing an order takes four calls across three APIs, in a specific order, passing an identifier from step two into step four. Agents rediscover that sequence by trial and error, and they get it wrong in ways that are expensive to debug and risky to run in production.

        We built API Workflows into our developer portal on top of Arazzo. Each workflow pairs an Arazzo definition, which tells an agent exactly how the calls chain together, with a natural language agent prompt that tells it when reaching for this workflow is the right move. Agents turn out to need both: the mechanics and the intent.

        The more interesting half is publication. One workflow is rendered in the portal for human developers and emitted into llms.txt and api-workflows.md for agents, with visibility controlled independently per audience and a draft-and-publish lifecycle so nothing reaches either surface early.

        This talk covers what Arazzo gave us for free, what we had to build around it, and why publishing outcome-level workflows beats handing agents a catalog of endpoints.

        Key Takeaways:

        - Why OpenAPI alone leaves agents guessing at multi-step business processes, and precisely what Arazzo adds
        - Pairing a machine-readable Arazzo definition with natural-language invocation guidance, and why agents need both
        - Publishing the same workflow to human and agent surfaces, including llms.txt and markdown, from one definition
        - Governing agent-facing capability with independent per-audience visibility and a publish lifecycle
        - Where Arazzo fit cleanly, where we had to extend around it, and what that suggests for the spec's evolution
        """.trimIndent(),
    ),
    session(
        day = 2,
        start = "14:30",
        end = "14:55",
        title = "From Validation to Semantic Guardrails: JSON Schema as the Contract Layer for Agentic Application Development",
        speakers = listOf("Jean-Pierre Dijcks", "Beda Hammerschmidt"),
        description = """
        Agentic coding systems do more than generate code snippets. They assemble applications, call tools, wire APIs, query databases, transform data, and produce user-facing experiences.
        As these systems move from assistance to action, they need more than prompt-level guardrails. They need data-connected guardrails: machine-readable contracts that describe what data means, how it may be used, where it may flow, and which policies apply.
        This talk presents Project Delphi, an authoritative JSON Schema repository, and proposes a new vocabulary for extending JSON Schema with semantic and governance annotations. Project Delphi is a concrete implementation, but the larger goal is broader: to explore how the JSON Schema community defines a shared, interoperable vocabulary for data semantics, policy, and AI-era application contracts.
        The core idea is simple: JSON Schema should remain the structural contract for data, while the vocabulary adds the semantic layer needed by agents, tools, and generated applications. This can describe document types, field roles, sensitivity classifications, PII/internal-only markers, lineage, retention, masking requirements, and AI usage permissions, as well as non-functional requirements.
        We will explore how this changes the application development model. Instead of an agent merely generating code against loosely described APIs, it can consult authoritative schemas and use semantic annotations to make policy-aware decisions: which fields may be shown, logged, embedded, summarized, joined, exported, or passed to a tool. Applications generated or modified by agents can then project data safely and consistently according to schema-level policy, rather than relying on scattered hand-coded checks.
        The session will cover a concrete technical model for schema-backed guardrails: registry-managed schemas, semantic annotations, field-level policy enforcement, safe data projection, tool-call contracts, compatibility checks, and runtime validation. We will close with open design questions and an invitation for the JSON Schema community to collaborate on DSV as a standards-oriented vocabulary rather than a vendor-specific extension.
        """.trimIndent(),
    ),
    session(
        day = 2,
        start = "15:00",
        end = "15:25",
        title = "From Schema Registry to Agent Registry: Governing AI Discovery with the A2A Protocol",
        speakers = listOf("Carles Arnal"),
        description = """
        API governance solved discovery, versioning, and compatibility for REST services. Now AI agents face the same challenges. One team updates a prompt template and downstream agents silently break. Welcome to the API governance problem all over again but for agents.

        This talk shows how Apicurio Registry (CNCF sandbox) evolved to manage AI-native artifacts using the same governance patterns that worked for APIs: A2A Agent Cards for discovery, LLM Model Schemas for metadata, and prompt templates with version control. Live demo: agents discovering each other through the A2A Protocol backed by registry-enforced compatibility rules.

        You'll see agent cards registered via REST API, agents discover peers using the same patterns as OpenAPI discovery, and the registry reject incompatible version pushes. This brings proven API governance patterns to multi-agent AI systems.

        Key message for API audience: if you solved API discovery and versioning, you already know how to solve agent discovery. The patterns are identical.
        """.trimIndent(),
    ),
    session(
        day = 2,
        start = "15:30",
        end = "15:55",
        title = "Event Contracts Are Domain Decisions",
        speakers = listOf("Annegret Junker"),
        description = """
        Ask an LLM to draft an AsyncAPI document from a domain model and you get a valid file in seconds. In
        one of our projects, one such draft came back bound to Kafka. The team had decided on RabbitMQ
        months earlier. Nobody had told the model, and nothing in the domain model could have: a good
        domain model knows nothing about brokers.
        That small mistake points at a bigger question. An AsyncAPI document records decisions: which events
        exist, what they mean, who may rely on them, and how they evolve. AI can write the YAML. It cannot
        make those decisions. So where do they come from, and how do they reach the spec intact?
        This talk follows the event contracts of Larder, a cooking platform we modeled and built end to end in a
        project. We start where the events are found: an Event Model that marks which interactions are
        synchronous and which are asynchronous. We add what a board leaves open with an asynchronous
        API Product Canvas, a refined Visual Glossary that carries invariants, and an ADR that fixes the broker.
        Then AI drafts the AsyncAPI. Every operation description reads like a sentence from the Domain Story,
        so domain experts can review the contract without translation.
        We compare two generated event contracts side by side: one derived from the canvas, one derived
        straight from the aggregates. We carry SOLID over to event contracts — backward compatibility as
        Liskov substitution over time, additive evolution with tolerant readers, one AsyncAPI per Bounded
        Context. And we show what tooling catches and what it cannot: a diff finds a removed field, but only a
        check against the Ubiquitous Language finds a field that quietly changed its meaning.
        Finally, the contract becomes the spine of implementation: publishers and consumers come from
        established AsyncAPI code generators and are wired test-first with an outbox relay, and contract tests
        guard every release.
        The message is simple: the promise of a Bounded Context is made by people — in the canvas, the
        glossary and the decisions. AI writes the boring part: the AsyncAPI document that records it.
        """.trimIndent(),
    ),
    service(day = 2, start = "15:55", end = "16:30", title = "Break"),
    session(
        day = 2,
        start = "16:30",
        end = "16:55",
        title = "GraphQL as the Execution Layer for AI Agents",
        speakers = listOf("Pascal Senn"),
    ),
    session(
        day = 2,
        start = "17:00",
        end = "17:25",
        title = "Your AI Agent Is Only as Good as Its Schema: Designing Reliable MCP Tools with JSON Schema",
        speakers = listOf("Ayodele Aransiola"),
        description = """
        AI agents are becoming active consumers of APIs, but many of the contracts they rely on were designed only for traditional applications and human developers. That creates ambiguity, unreliable tool calls, inconsistent outputs, and unnecessary complexity when teams introduce MCP or other agent-based systems.

        This talk explores why JSON Schema is becoming critical infrastructure for reliable AI tooling. Using MCP tools as a practical example, I’ll show how well-designed schemas can make agent interactions more predictable, improve validation, reduce ambiguity, and create clearer contracts between models and backend systems.

        The goal is to move the conversation beyond prompts and model choice, and focus on something more fundamental: if an agent cannot clearly understand what a tool accepts and returns, the quality of the model alone will not make the system reliable.
        """.trimIndent(),
    ),
    session(
        day = 2,
        start = "17:30",
        end = "17:55",
        title = "OpenAPI: The Answer Key for MCP Tool Calling",
        speakers = listOf("Miguel Quintero"),
        description = """
        Your MCP server passes its unit tests. Then an agent connects, picks the wrong tool, sends a well-formed date for the wrong week, and reports success. Failures like these come from how a model reads your tool names, descriptions, and schemas, and most teams have no way to measure them before release. This talk argues that the answer key already exists: your OpenAPI description. The same document that generates your MCP tools can grade how agents use them. Examples become golden test cases. JSON Schema becomes argument assertions. HTTP semantics and deprecation metadata become safety gates. We'll walk through a pipeline that lints the spec for agent readiness, generates the tools, scores tool selection, arguments, and task completion, and blocks a release in CI when an unsafe call slips through. You'll leave with a mapping from OpenAPI fields to eval signals you can apply to your own specs, a pipeline that fits into existing CI, and a case for holding destructive and permission-boundary calls to a zero-tolerance threshold instead of a blended pass rate.
        """.trimIndent(),
    ),

    // Day 3: December 3
    session(
        day = 3,
        start = "09:15",
        end = "09:40",
        title = "Beyond the Query: GraphQL Features That Make Frontends Simpler",
        speakers = listOf("Aurelien David"),
    ),
    session(
        day = 3,
        start = "09:45",
        end = "10:10",
        title = "Beyond REST: Evolving Arazzo for Real-World Agentic Workflows",
        speakers = listOf("Naresh Jain"),
        description = """
        A real-world business workflow rarely stays within the boundaries of a single API style.

        An order might begin with an HTTP API described using OpenAPI, trigger an event described using AsyncAPI, invoke a Protobuf RPC service, query data through GraphQL, and eventually receive an asynchronous notification. Agentic applications make this even more important: an agent needs to understand not just individual API operations, but how capabilities across heterogeneous systems can be composed into a meaningful workflow.

        Arazzo was created to describe sequences of API calls and the dependencies between them. But what happens when the next step isn’t another OpenAPI operation?

        In this talk, we’ll look at how Arazzo is evolving to support heterogeneous API interaction models. Drawing on the work to introduce AsyncAPI, Protocol Buffer RPC and GraphQL support into the Arazzo Specification, we’ll explore some of the surprisingly difficult design questions that emerge when REST-style request/response assumptions disappear.

        How do you represent send and receive operations in an event-driven system? What does a workflow step mean when an RPC is server-streaming or bidirectional? How should GraphQL queries, mutations and subscriptions participate in a workflow? And how can we add these capabilities without turning Arazzo into a collection of protocol-specific special cases?

        We’ll use these questions to explore a broader idea: Arazzo can become a protocol-independent description of how API capabilities are composed to accomplish an outcome.

        That has implications beyond API documentation and testing. For AI agents, an Arazzo workflow can provide something increasingly valuable: an explicit, machine-readable description of which capabilities to invoke, in what sequence, how data flows between them, and what successful execution looks like.

        Rather than asking agents to infer workflows from a collection of disconnected API descriptions, we can start making the workflow itself part of the contract.
        """.trimIndent(),
    ),
    session(
        day = 3,
        start = "10:15",
        end = "10:40",
        title = "Houston, We Have a Partial Response: GraphQL Error Handling",
        speakers = listOf("Željko Kozina"),
    ),
    service(day = 3, start = "10:40", end = "11:00", title = "Break"),
    session(
        day = 3,
        start = "11:00",
        end = "11:25",
        title = "Let’s Help Agents to Navigate the API and the Data World",
        speakers = listOf("Simon Harrer"),
        description = """
        AI agents work across two worlds. Data lives in tables, and business functions live behind APIs. Real tasks need both. "Give every customer with a late delivery a voucher" means querying shipment data first and then calling a voucher API. The table calls the customer customer_id and the API calls it customerNumber. Nothing tells the agent that both mean the same customer, so it has to guess. A wrong guess means a wrong join or a wrong action.

        This talk covers three open standards that together close this gap:
        - The Open Data Contract Standard (ODCS) describes the data world: tables, columns, types, quality, and terms of use.
        - OpenAPI describes the API world: endpoints, requests, and responses.
        - Apache Ossie describes the meaning: business concepts such as Customer, Shipment, and Voucher, and how they relate.

        ODCS and OpenAPI describe the shape of the data, and Ossie describes what it means. When columns in data contracts and fields in API specifications link to the same Ossie concepts, the agent can start from the concepts in its task. The ontology leads it to the right data product and the right API, so it can combine both without guessing.

        We go through the voucher example step by step, with real files for all three standards, and discuss how teams build and maintain the ontology without a central bottleneck.

        You will learn:
        - what ODCS, OpenAPI, and Apache Ossie each describe, and where each one stops
        - how to link data contract columns and API fields to concepts in one Ossie ontology
        - how an agent uses these links to read data and then act through APIs
        """.trimIndent(),
    ),
    session(
        day = 3,
        start = "11:30",
        end = "11:55",
        title = "Foundational JSON Schema",
        speakers = listOf("Ben Hutton"),
        description = """
        New to JSON Schema? Think you know enough JSON Schema? Think AI can do all your JSON Schema for you? When things go wrong, you'll need a good foundational understanding to make sure it does what you think it does, or debug why it's not working like it should.
        """.trimIndent(),
    ),
    session(
        day = 3,
        start = "12:00",
        end = "12:25",
        title = "GraphQL with AWS AppSync - Building a Product API at HEMA",
        speakers = listOf("Ivan Jančić", "Bart Rijksen"),
        description = """
        I will talk about "pioneer phase" of GraphQL adoption at HEMA and using AWS AppSync for the composable commerce migration, where I will share the raw and honest lessons learned when introducing a new tech stack to a 100-year-old Dutch icon.
        """.trimIndent(),
    ),
    session(
        day = 3,
        start = "12:30",
        end = "12:55",
        title = "The Risks That Come with Underfunded API Standards",
        speakers = listOf("Thulisile Sibanda"),
        description = """
        The technology industry is investing heavily in AI, accelerating software development and relying on increasingly interconnected systems. Beneath this rapid evolution lies shared digital infrastructure: the open standards, specifications, and APIs that enable software ecosystems to communicate, innovate, and scale. However, the resources required to sustain this critical infrastructure are failing to keep pace with industry adoption.

        When funding is uncertain, and only a few people remain to maintain these standards, the problem becomes more than financial; it becomes a security risk. Without enough support, API specifications go unfunded, security patches are delayed, and governance is overlooked. This makes software supply chains more vulnerable, especially as businesses rely on these systems more than ever.

        Drawing on real-world community insights from the AsyncAPI initiative and the broader open-source ecosystem, this session challenges the assumption that shared technical infrastructure will remain resilient simply because it is open. We will explore the friction between massive commercial adoption and maintainer capacity, examine the cybersecurity risks of neglected open specifications, and discuss actionable models for long-term sustainability, governance, and collective industry stewardship.
        """.trimIndent(),
    ),
    service(day = 3, start = "12:55", end = "14:00", title = "Lunch"),
    session(
        day = 3,
        start = "14:00",
        end = "14:25",
        title = "TBA",
        speakers = listOf("Matthew Adams"),
    ),
    session(
        day = 3,
        start = "14:30",
        end = "15:25",
        title = "AsyncAPI Ambassador Panel - Can AsyncAPI help AI Agents Speak Event-Driven?",
        speakers = listOf("Hugo Guerrero", "Naresh Jain", "Ludovic Dussart"),
    ),
    service(day = 3, start = "15:25", end = "15:55", title = "Break"),
    session(
        day = 3,
        start = "15:55",
        end = "16:50",
        title = "Unconference: API standards 2027 working group",
    ),
)

internal val allDays: List<Day> = dates.map { (number, date) -> Day(number, LocalDate.parse(date)) }

internal val allSessions: List<Session> = allSlots.filterIsInstance<Session>()

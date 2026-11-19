package apistandards

/**
 * Session descriptions and speaker details, generated from
 * "API Standards Conference - FOST Paris 2026 - Speakers.csv".
 *
 * Cleanup applied: whitespace trimmed, "TBD" values dropped, LinkedIn URLs normalized,
 * Lukasz Gornicki's job title and company un-swapped, stray "||" removed from Gabriela Colombo's job title.
 * Hugo Guerrero and Ludovic Dussart are not in the CSV.
 */
internal object AgendaDetails {
    class SpeakerDetails(val title: String?, val company: String?, val bio: String?, val linkedin: String?)

    /**
     * By session id
     */
    val sessionDescriptions: Map<String, String> = mapOf(
        "day1-1100" to "OpenAPI has seen a lot of success since it was created as Swagger in 2010 and became OpenAPI in 2016. What is the reason for its continued momentum and how does the landscape change with AI entering the picture in a variety of ways? We look at how OpenAPI can help you, how OAI can help you using OpenAPI better, and how AI means that you have to slightly adjust the way you design and manage your APIs.",
        "day1-1130" to "And as Arazzo is an open specification it makes those improvements portable and vendor-independent, turning enterprise workflow knowledge into durable, auditable infrastructure for the agentic era.",
        "day1-1200" to "Open-source packages are now the primary attack surface used by threat actors. Rather than targeting enterprises directly, attackers are now compromising widely-used packages with a goal of reaching millions of CI/CD systems simultaneously through trusted releases.\n\nBecause these open-source packages form the backbone of modern enterprise workflows, including cloud pipelines, CI/CD systems, and developer tooling, attackers are increasingly targeting open-source organizations to poison these packages and publish them to trusted registries such as npm and the VS Code Marketplace, compromising hundreds of downstream organizations simultaneously through trusted releases installed by their developers.\n\nOver the past year, AsyncAPI faced two supply chain attacks by **TeamPCP** and **Miasma**, compromising packages with up to 4M+ combined weekly downloads, such as:\n\n- @asyncapi/specs (4M+ weekly)\n- @asyncapi/generator (~130k weekly)\n- Official VS Code extensions\n\nThis session presents the tactics, techniques, and procedures (TTPs) we saw used in both incidents. We will trace how a single compromised release propagates into enterprise and public sector environments across Europe and France.\n\nFinally, attendees will gain actionable defense strategies and tools we have been using to tighten our security measures. We will showcase how we overhauled our release pipelines using:\n\n- OIDC Trusted Publishing\n- Automated CI/CD security scanning of every workflow change before it can release\n- Hardened token isolation\n- SHA-pinned GitHub Actions\n- Least-privilege permission scopes on automation bots\n\nAttendees will leave with practical resources and tools to audit their own GitHub Actions, build pipelines, and IDE extension supply chains against active threat groups.\n\n**The Technical Incidents:**\n\nThis section of the talk will provides first-hand incident response account from the AsyncAPI Initiative security team following two real-world software supply-chain compromises orchestrated by threat groups TeamPCP and Miasma.\n\n- Exploitation of GitHub Actions pull_request_target workflows (pwn requests) to steal elevated repository secrets.\n- Spam pull requests used to hide the real attack in the workflow logs, and a docs PR using an MDX file to run attacker code in our build pipeline.\n- How the attackers released compromised packages with trusted credentials releasing malicious builds of @asyncapi/specs, @asyncapi/generator, and developer VS Code extensions directly to npm and the VS Code Marketplace, including one release through our own OIDC pipeline.\n\n**Direct Impact of Supply Chain Attacks for Financial Sector Attendees in France and the EU:**\n\nFor banking, fintech, and public sector ecosystems in France and the EU operating under strict EU regulatory frameworks like DORA and NIS2, open-source packages and developer extensions represent an unmonitored third-party dependency vector. A poisoned upstream library imported into an internal financial pipeline or a developer’s IDE circumvents traditional perimeter controls.\n\n**Actionable Takeaways:**\n\nAttendees will leave with practical blueprints we implemented to remediate and harden our release infrastructure and further insights on how to protect their organizations from being compromised through supply chain attacks.",
        "day1-1230" to "Open Resource Discovery (ORD) addresses this with an open standard — governed by the Linux Foundation (NeoNephos Project) — for decentralized, self-describing systems. Instead of registering everything in a central catalog by hand, systems publish machine-readable metadata at the source, so catalogs, tools, and AI agents can crawl and refresh it automatically from a single entry point. ORD complements OpenAPI and AsyncAPI rather than replacing them, adding the high-level context and relationships that make APIs — along with events, data products, and AI resources such as agents, skills, and workflows — automatically discoverable, together with their contracts and documentation.\n\nORD is also built for AI consumers: dedicated AI-facing metadata (such as hints for LLMs and agent orchestrators) and native support for agent protocols let AI orchestrators dynamically discover the capabilities available to them in a system landscape.\n\nWith ORD, organizations can publish their metadata offerings, analyze their IT landscape in real time, and build platforms for software development, data management, and AI.",
        "day1-1400" to "Most JSON Schema implementations evaluate keywords. A validator checks a value against \"minimum\" or \"properties\" and reports whether it passed. That's enough for validation, but other questions need more. Does this schema allow anything at all? Does this \"minimum\" contradict that \"maximum\"? Is this required property one that \"additionalProperties: false\" forbids? Answering those means reasoning about what a schema means, not just evaluating it.\n\nVersion 2 of @hyperjump/json-schema is built around that idea. Every keyword, in every dialect from draft-04 to JSON Schema v1, lowers to a kernel: a small language of operations with a normal form. The validator runs the kernel, the optimizer rewrites it, and the output formats explain it. The linter's 48 rules read it to answer questions like the ones above. None of them keeps its own facts about keywords. What a keyword does comes from what it lowers to, so a rule written once works in every dialect. That includes custom keywords. A custom keyword is defined by what it lowers to, so the linter and optimizer understand it like a built-in one, with no extra code.\n\nUnderstanding a schema also makes it fast. On the jsonschema-benchmark corpus, v2 validates 14 times faster than v1 and about 4 times faster than Ajv. It does that without generating code: no \"eval\" or \"new Function\", so it runs under a strict Content Security Policy.\n\nThis talk shows what lowering looks like and how it makes dialects comparable. It covers how the optimizer gets speed without code generation, and how lint rules work, down to the kernel expressions they read. Attendees will leave with a way of thinking about schema semantics they can use in their own implementations and tools.",
        "day1-1430" to "Arazzo changes the unit of exchange. By composing API operations into business-defined workflows with typed inputs, success criteria and structured failure handling, Arazzo lets organisations expose what agents actually need: goal-oriented capabilities, not a catalogue of primitives. Those workflows become first-class MCP tools — derived directly from the Arazzo document, with no hard-coded tool definitions to write or lifecycle to manage. Descriptions, typed schemas and examples are generated from the workflow itself, reducing search-and-execute overhead and guiding agents toward completion rather than exploration.",
        "day1-1500" to "Event gateways are becoming a popular answer to a real problem in event-driven architecture: the lack of a clear control plane.\nBut they should not be understood as API gateways for events. Events have different failure modes. Ownership is less visible. Compatibility changes over time. Policies are harder to enforce without coupling producers and consumers again. Observability depends on more than traffic flowing through a single point.\nThis talk examines where event gateways are useful, where brokers, schema registries, catalogs, and portals already cover part of the problem, and where the gateway metaphor starts to break down.\nThe focus is not another infrastructure layer. The focus is which governance capabilities event-driven systems actually need, and how AsyncAPI can help describe them.",
        "day1-1530" to "Giving agents raw API operations is like handing someone a parts list instead of a finished product. Individually the operations are valid but assembled in the wrong order, with the wrong inputs, they produce the wrong outcomes. This is wasteful, unpredictable, and incurs a real cost on both sides of the interaction.",
        "day1-1630" to "A schema answers the question: is this finished document valid? However, an editor often sees an unfinished document. While someone types, the file is not yet valid, and the editor still has to answer four questions:\n\n1. Where am I in a half-typed, broken document?\n2. What could go here?\n3. What actually went wrong?\n4. What does this part of the schema mean?\n\nThis talk works through each question using the JSON Schema support behind hover, completion, go-to-definition and diagnostics in KSON, an open-source configuration language.\n\nSome examples: an `if` that depends on a field you haven't typed yet. A single typo in a `oneOf` that gives you an error for every branch, even though `const` and `enum` could tell the editor which branch you meant. A property with three descriptions, and no rule for which one hover shows.\n\nEach section ends with a rule schema authors can apply today.",
        "day1-1700" to "Frontier models can now judge a single OpenAPI change correctly almost every time. Will general LLMs replace dedicated tools? We took oasdiff, an open-source OpenAPI breaking-change checker, as a test case and ran the experiment. Three Claude models faced oasdiff on six spec changes, five of them from real bug reports. The best model was right on all five cases the checker had missed. The smaller ones changed their verdict on identical input or applied the wrong rule with confidence. On the 44 MB Microsoft Graph spec, far beyond any context window, agents matched oasdiff's 521 affected operations only by writing a one-off program for that one kind of change. And when Claude fixed one of the bugs it had found, it introduced a new one that only review caught. The results fit a maturity model for turning intelligence into a durable capability: ask the model for each answer, have it write a throwaway program, have it develop or enhance a maintained tool under review where every failure becomes a test, then call it only for cases the tool cannot decide. Each step trades breadth for what a CI gate needs: every change covered, the same answer every run, stable findings, explicit policy, near-zero cost. Climbing these modes is the maintainers' job, not the users'. A shared tool also pools what every team learns, so one team's edge case becomes everyone's rule. Better models make such tools cheaper to build: expect more of them, not fewer.",
        "day2-1150" to "One of the most important tools to AI and APIs is now the subject matter of an active standardization Working Group at the IETF. Yet there's no reason to cut off innovation and extensions. How is this going to work? Who can contribute? (hint: everyone). Get informed, maybe even get involved.",
        "day2-1220" to "Migrating an event-driven architecture isn't just a change of broker. It means renegotiating contracts between every producer and consumer. In this talk, we share our journey from a legacy EDA built on GCP Pub/Sub and Avro to one built on AWS EventBridge, where every event follows the CloudEvents standard and the entire event API is defined by an AsyncAPI spec. We'll also show how those same events now power our data products.\n\nWe'll cover:\n\nWhy we changed. Routing messages on our legacy stack was hard, the setup carried too much infrastructure to maintain, and Avro depended on a schema registry we didn't control.\nContract-first events with AsyncAPI. We govern our event schemas contract-first, the same way we govern our REST APIs with OpenAPI, with the spec as the source of truth. We'll show how that replaced the external schema registry and what the workflow looks like day to day.\nFrom events to data products. Because every event has a well-defined contract, the same events that drive our services land in our data lakehouse, where they're transformed into data marts and continuously monitored for quality.\n\nAttendees will leave with practical lessons on using AsyncAPI to make events a reliable foundation for both services and data products.",
        "day2-1400" to "OpenAPI tells an agent which operations exist. It does not tell it that placing an order takes four calls across three APIs, in a specific order, passing an identifier from step two into step four. Agents rediscover that sequence by trial and error, and they get it wrong in ways that are expensive to debug and risky to run in production.\n\nWe built API Workflows into our developer portal on top of Arazzo. Each workflow pairs an Arazzo definition, which tells an agent exactly how the calls chain together, with a natural language agent prompt that tells it when reaching for this workflow is the right move. Agents turn out to need both: the mechanics and the intent.\n\nThe more interesting half is publication. One workflow is rendered in the portal for human developers and emitted into llms.txt and api-workflows.md for agents, with visibility controlled independently per audience and a draft-and-publish lifecycle so nothing reaches either surface early.\n\nThis talk covers what Arazzo gave us for free, what we had to build around it, and why publishing outcome-level workflows beats handing agents a catalog of endpoints.\n\nKey Takeaways:\n\n- Why OpenAPI alone leaves agents guessing at multi-step business processes, and precisely what Arazzo adds\n- Pairing a machine-readable Arazzo definition with natural-language invocation guidance, and why agents need both\n- Publishing the same workflow to human and agent surfaces, including llms.txt and markdown, from one definition\n- Governing agent-facing capability with independent per-audience visibility and a publish lifecycle\n- Where Arazzo fit cleanly, where we had to extend around it, and what that suggests for the spec's evolution",
        "day2-1430" to "Agentic coding systems do more than generate code snippets. They assemble applications, call tools, wire APIs, query databases, transform data, and produce user-facing experiences.\nAs these systems move from assistance to action, they need more than prompt-level guardrails. They need data-connected guardrails: machine-readable contracts that describe what data means, how it may be used, where it may flow, and which policies apply.\nThis talk presents Project Delphi, an authoritative JSON Schema repository, and proposes a new vocabulary for extending JSON Schema with semantic and governance annotations. Project Delphi is a concrete implementation, but the larger goal is broader: to explore how the JSON Schema community defines a shared, interoperable vocabulary for data semantics, policy, and AI-era application contracts.\nThe core idea is simple: JSON Schema should remain the structural contract for data, while the vocabulary adds the semantic layer needed by agents, tools, and generated applications. This can describe document types, field roles, sensitivity classifications, PII/internal-only markers, lineage, retention, masking requirements, and AI usage permissions, as well as non-functional requirements.\nWe will explore how this changes the application development model. Instead of an agent merely generating code against loosely described APIs, it can consult authoritative schemas and use semantic annotations to make policy-aware decisions: which fields may be shown, logged, embedded, summarized, joined, exported, or passed to a tool. Applications generated or modified by agents can then project data safely and consistently according to schema-level policy, rather than relying on scattered hand-coded checks.\nThe session will cover a concrete technical model for schema-backed guardrails: registry-managed schemas, semantic annotations, field-level policy enforcement, safe data projection, tool-call contracts, compatibility checks, and runtime validation. We will close with open design questions and an invitation for the JSON Schema community to collaborate on DSV as a standards-oriented vocabulary rather than a vendor-specific extension.",
        "day2-1500" to "API governance solved discovery, versioning, and compatibility for REST services. Now AI agents face the same challenges. One team updates a prompt template and downstream agents silently break. Welcome to the API governance problem all over again but for agents.\n\nThis talk shows how Apicurio Registry (CNCF sandbox) evolved to manage AI-native artifacts using the same governance patterns that worked for APIs: A2A Agent Cards for discovery, LLM Model Schemas for metadata, and prompt templates with version control. Live demo: agents discovering each other through the A2A Protocol backed by registry-enforced compatibility rules.\n\nYou'll see agent cards registered via REST API, agents discover peers using the same patterns as OpenAPI discovery, and the registry reject incompatible version pushes. This brings proven API governance patterns to multi-agent AI systems.\n\nKey message for API audience: if you solved API discovery and versioning, you already know how to solve agent discovery. The patterns are identical.",
        "day2-1530" to "Ask an LLM to draft an AsyncAPI document from a domain model and you get a valid file in seconds. In\none of our projects, one such draft came back bound to Kafka. The team had decided on RabbitMQ\nmonths earlier. Nobody had told the model, and nothing in the domain model could have: a good\ndomain model knows nothing about brokers.\nThat small mistake points at a bigger question. An AsyncAPI document records decisions: which events\nexist, what they mean, who may rely on them, and how they evolve. AI can write the YAML. It cannot\nmake those decisions. So where do they come from, and how do they reach the spec intact?\nThis talk follows the event contracts of Larder, a cooking platform we modeled and built end to end in a\nproject. We start where the events are found: an Event Model that marks which interactions are\nsynchronous and which are asynchronous. We add what a board leaves open with an asynchronous\nAPI Product Canvas, a refined Visual Glossary that carries invariants, and an ADR that fixes the broker.\nThen AI drafts the AsyncAPI. Every operation description reads like a sentence from the Domain Story,\nso domain experts can review the contract without translation.\nWe compare two generated event contracts side by side: one derived from the canvas, one derived\nstraight from the aggregates. We carry SOLID over to event contracts — backward compatibility as\nLiskov substitution over time, additive evolution with tolerant readers, one AsyncAPI per Bounded\nContext. And we show what tooling catches and what it cannot: a diff finds a removed field, but only a\ncheck against the Ubiquitous Language finds a field that quietly changed its meaning.\nFinally, the contract becomes the spine of implementation: publishers and consumers come from\nestablished AsyncAPI code generators and are wired test-first with an outbox relay, and contract tests\nguard every release.\nThe message is simple: the promise of a Bounded Context is made by people — in the canvas, the\nglossary and the decisions. AI writes the boring part: the AsyncAPI document that records it.",
        "day2-1700" to "AI agents are becoming active consumers of APIs, but many of the contracts they rely on were designed only for traditional applications and human developers. That creates ambiguity, unreliable tool calls, inconsistent outputs, and unnecessary complexity when teams introduce MCP or other agent-based systems.\n\nThis talk explores why JSON Schema is becoming critical infrastructure for reliable AI tooling. Using MCP tools as a practical example, I’ll show how well-designed schemas can make agent interactions more predictable, improve validation, reduce ambiguity, and create clearer contracts between models and backend systems.\n\nThe goal is to move the conversation beyond prompts and model choice, and focus on something more fundamental: if an agent cannot clearly understand what a tool accepts and returns, the quality of the model alone will not make the system reliable.",
        "day2-1730" to "Your MCP server passes its unit tests. Then an agent connects, picks the wrong tool, sends a well-formed date for the wrong week, and reports success. Failures like these come from how a model reads your tool names, descriptions, and schemas, and most teams have no way to measure them before release. This talk argues that the answer key already exists: your OpenAPI description. The same document that generates your MCP tools can grade how agents use them. Examples become golden test cases. JSON Schema becomes argument assertions. HTTP semantics and deprecation metadata become safety gates. We'll walk through a pipeline that lints the spec for agent readiness, generates the tools, scores tool selection, arguments, and task completion, and blocks a release in CI when an unsafe call slips through. You'll leave with a mapping from OpenAPI fields to eval signals you can apply to your own specs, a pipeline that fits into existing CI, and a case for holding destructive and permission-boundary calls to a zero-tolerance threshold instead of a blended pass rate.",
        "day3-0945" to "A real-world business workflow rarely stays within the boundaries of a single API style.\n\nAn order might begin with an HTTP API described using OpenAPI, trigger an event described using AsyncAPI, invoke a Protobuf RPC service, query data through GraphQL, and eventually receive an asynchronous notification. Agentic applications make this even more important: an agent needs to understand not just individual API operations, but how capabilities across heterogeneous systems can be composed into a meaningful workflow.\n\nArazzo was created to describe sequences of API calls and the dependencies between them. But what happens when the next step isn’t another OpenAPI operation?\n\nIn this talk, we’ll look at how Arazzo is evolving to support heterogeneous API interaction models. Drawing on the work to introduce AsyncAPI, Protocol Buffer RPC and GraphQL support into the Arazzo Specification, we’ll explore some of the surprisingly difficult design questions that emerge when REST-style request/response assumptions disappear.\n\nHow do you represent send and receive operations in an event-driven system? What does a workflow step mean when an RPC is server-streaming or bidirectional? How should GraphQL queries, mutations and subscriptions participate in a workflow? And how can we add these capabilities without turning Arazzo into a collection of protocol-specific special cases?\n\nWe’ll use these questions to explore a broader idea: Arazzo can become a protocol-independent description of how API capabilities are composed to accomplish an outcome.\n\nThat has implications beyond API documentation and testing. For AI agents, an Arazzo workflow can provide something increasingly valuable: an explicit, machine-readable description of which capabilities to invoke, in what sequence, how data flows between them, and what successful execution looks like.\n\nRather than asking agents to infer workflows from a collection of disconnected API descriptions, we can start making the workflow itself part of the contract.",
        "day3-1015" to "The difference is measurable. This talk walks through how workflow-backed MCP tools affect task completion rates, token efficiency and predictable execution cost compared to operation-by-operation exposure, and in a manner that can be verified across models, so the improvements hold regardless of which LLM is doing the work. Evals become more possible which turns \"it worked in testing\" into \"we know it works at the boundary, consistently and within budget.\"",
        "day3-1100" to "AI agents work across two worlds. Data lives in tables, and business functions live behind APIs. Real tasks need both. \"Give every customer with a late delivery a voucher\" means querying shipment data first and then calling a voucher API. The table calls the customer customer_id and the API calls it customerNumber. Nothing tells the agent that both mean the same customer, so it has to guess. A wrong guess means a wrong join or a wrong action.\n\nThis talk covers three open standards that together close this gap:\n- The Open Data Contract Standard (ODCS) describes the data world: tables, columns, types, quality, and terms of use.\n- OpenAPI describes the API world: endpoints, requests, and responses.\n- Apache Ossie describes the meaning: business concepts such as Customer, Shipment, and Voucher, and how they relate.\n\nODCS and OpenAPI describe the shape of the data, and Ossie describes what it means. When columns in data contracts and fields in API specifications link to the same Ossie concepts, the agent can start from the concepts in its task. The ontology leads it to the right data product and the right API, so it can combine both without guessing.\n\nWe go through the voucher example step by step, with real files for all three standards, and discuss how teams build and maintain the ontology without a central bottleneck.\n\nYou will learn:\n- what ODCS, OpenAPI, and Apache Ossie each describe, and where each one stops\n- how to link data contract columns and API fields to concepts in one Ossie ontology\n- how an agent uses these links to read data and then act through APIs",
        "day3-1130" to "New to JSON Schema? Think you know enough JSON Schema? Think AI can do all your JSON Schema for you? When things go wrong, you'll need a good foundational understanding to make sure it does what you think it does, or debug why it's not working like it should.",
        "day3-1200" to "I will talk about \"pioneer phase\" of GraphQL adoption at HEMA and using AWS AppSync for the composable commerce migration, where I will share the raw and honest lessons learned when introducing a new tech stack to a 100-year-old Dutch icon.",
        "day3-1230" to "The technology industry is investing heavily in AI, accelerating software development and relying on increasingly interconnected systems. Beneath this rapid evolution lies shared digital infrastructure: the open standards, specifications, and APIs that enable software ecosystems to communicate, innovate, and scale. However, the resources required to sustain this critical infrastructure are failing to keep pace with industry adoption.\n\nWhen funding is uncertain, and only a few people remain to maintain these standards, the problem becomes more than financial; it becomes a security risk. Without enough support, API specifications go unfunded, security patches are delayed, and governance is overlooked. This makes software supply chains more vulnerable, especially as businesses rely on these systems more than ever.\n\nDrawing on real-world community insights from the AsyncAPI initiative and the broader open-source ecosystem, this session challenges the assumption that shared technical infrastructure will remain resilient simply because it is open. We will explore the friction between massive commercial adoption and maintainer capacity, examine the cybersecurity risks of neglected open specifications, and discuss actionable models for long-term sustainability, governance, and collective industry stewardship.",
    )

    /**
     * By speaker name
     */
    val speakers: Map<String, SpeakerDetails> = mapOf(
        "Annegret Junker" to SpeakerDetails(
            title = "Chief Software Architect",
            company = "codecentric AG",
            bio = null,
            linkedin = "https://www.linkedin.com/in/dr-annegret-junker-141a99a4/",
        ),
        "Aurelien David" to SpeakerDetails(
            title = "Engineering Manager",
            company = "Pennylane",
            bio = "Co-founder & CTO heybilly.io acuired by Pennylane - GraphQL foundation ambassador",
            linkedin = "https://www.linkedin.com/in/aurel-spyl/",
        ),
        "Ayodele Aransiola" to SpeakerDetails(
            title = "Solutions Architect",
            company = "Gopaddi",
            bio = "Ayodele is a solution architect and a passionate community builder who shares his wealth of knowledge on architecting scalable products, and software engineering practices.",
            linkedin = "https://www.linkedin.com/in/aransiolaayo/",
        ),
        "Bart Dubbeldam" to SpeakerDetails(
            title = "Founding Engineer",
            company = "Cached Intelligence",
            bio = "I'm Bart, Founding Engineer at Cached Intelligence and a core contributor to KSON. I enjoy solving complex problems across various domains. This has taken me from web development and data analytics to TLS in rustls, git internals in gitoxide and package management in pixi. Away from the keyboard I play chess and used to compete as a professional grappler.",
            linkedin = null,
        ),
        "Bart Rijksen" to SpeakerDetails(
            title = "Data Scientist",
            company = "HEMA",
            bio = null,
            linkedin = "https://www.linkedin.com/in/bart-rijksen-877785a/",
        ),
        "Beda Hammerschmidt" to SpeakerDetails(
            title = "VP JSON Development",
            company = "Oracle",
            bio = null,
            linkedin = "https://www.linkedin.com/in/beda/",
        ),
        "Ben Hutton" to SpeakerDetails(
            title = "Staff Backend Engineer",
            company = "Spoke / JSON Schema TSC",
            bio = "Most excited by work that enables. Pragmatist. Love to be proved wrong.",
            linkedin = "https://www.linkedin.com/in/benhuttonuk/",
        ),
        "Camille Lawrence" to SpeakerDetails(
            title = "Senior Software Engineer",
            company = "Apollo GraphQL",
            bio = "Camille Lawrence is a Senior Software Engineer at Apollo GraphQL, where she maintains Apollo MCP Server, the open-source server that turns GraphQL operations into tools for AI agents. She also contributes to the official Rust MCP SDK and is a member of the Agentic AI Foundation's Workflows & Process Integration Working Group. Before Apollo, she was a Staff Software Engineer at CircleCI. She has spent over a decade building developer tools at companies including 1Password, Shopify and Telus.",
            linkedin = "https://www.linkedin.com/in/camillelawrence/",
        ),
        "Carles Arnal" to SpeakerDetails(
            title = "Principal Software Engineer",
            company = "IBM",
            bio = null,
            linkedin = "https://www.linkedin.com/in/carles-arnal-824b44a5/",
        ),
        "Daniel Kocot" to SpeakerDetails(
            title = "Principal Integration Architect",
            company = "adorsys GmbH & Co. KG",
            bio = "Daniel Kocot has been part of the adorsys GmbH & Co. KG team as a Principal Integration Architect since January 2026. For nearly ten years, he has been dedicated to topics around integration. He began with a strong focus on gateways and the early days of API operations (APIOps), and later shifted his focus toward methodologies, strategy, and enterprise architecture.",
            linkedin = "https://www.linkedin.com/in/danielkocot/",
        ),
        "David Stutt" to SpeakerDetails(
            title = "Senior Founding Engineer",
            company = "Wundergraph",
            bio = "David is one of the four founding engineers at WunderGraph who collectively built the software foundations of the company. David's career began in the finance sector of software engineering before moving exclusively to API management in 2022. From that point, David started his deep-dive journey into GraphQL and Federation. David was also the creator of the popular \"Will It Compose\" game that was presented by Jens Neuse at GraphQL Conf 2023 at the beginning of his Dataloader 3.0 session, which remains the most-watched video on the GraphQL Working Groups YouTube channel. Recently, David gave a talk on \"top-down\" schema design at GraphQL Conf 2026—Federation, Reversed: A Consumer-First Future with Fission . David is also a member of the composite schema working group committee and tries to provide contributions and input where possible.",
            linkedin = "https://www.linkedin.com/in/davidstutt/",
        ),
        "Erik Wilde" to SpeakerDetails(
            title = "Chief Evangelist",
            company = "WunderGraph",
            bio = null,
            linkedin = null,
        ),
        "Florence Njeri" to SpeakerDetails(
            title = "Cybersecurity Engineer",
            company = "AsyncAPI Initiative",
            bio = "Florence Njeri is a Cybersecurity Engineer at the AsyncAPI Initiative where she was part of the incident response for both the Shai-Hulud byTeamPCP and Miasma supply chain attacks, which compromised packages with millions of weekly downloads.",
            linkedin = "https://www.linkedin.com/in/florencenjeri/",
        ),
        "Frank Kilcommins" to SpeakerDetails(
            title = "Head of Enterprise Architecture",
            company = "Jentic",
            bio = null,
            linkedin = "https://www.linkedin.com/in/frank-kilcommins/",
        ),
        "Gabriela Colombo" to SpeakerDetails(
            title = "Software Engineer",
            company = "Swapcard",
            bio = "I'm a backend software engineer at Swapcard, an event technology platform used by organizers worldwide. I build distributed services and GraphQL APIs in Node.js and TypeScript, focusing on schema evolution, data consistency, and performance.",
            linkedin = "https://www.linkedin.com/in/gabrielacolomboalveslongo/",
        ),
        "Galina Tuova" to SpeakerDetails(
            title = "Product Manager - Metadata & Domain Alignment CoE",
            company = "SAP",
            bio = null,
            linkedin = "https://www.linkedin.com/in/galina-tuova-178563157/",
        ),
        "Ignacio Castillejos" to SpeakerDetails(
            title = "Staff Engineer",
            company = "The New York Times",
            bio = "Ignacio Castillejos is a Staff Engineer at The New York Times, where he focuses on event-driven architecture, subscription modernization, and building strong engineering cultures. With a career spanning tech giants like Nike and Motorola, Ignacio bridges the gap between deep technical capabilities including extensive expertise in AWS ecosystems and strategic product management.\n\nHe is passionate about process improvement, and developing high-performing, distributed engineering teams. A lifelong learner holding dual master's degrees in Software Engineering and Tech Management, Ignacio loves sharing practical insights on architecture, technical leadership, and scaling global digital platforms.",
            linkedin = "https://www.linkedin.com/in/icast7/",
        ),
        "Ivan Jančić" to SpeakerDetails(
            title = "Solutions Architect",
            company = "Brighting",
            bio = "I’m a Solutions Architect and backend engineer focused on building scalable cloud systems and modern distributed architectures. My background is centered around cloud, microservices and event-driven systems.\n\nI hold a Master’s degree in Applied Computer Science and multiple AWS certifications including the AWS Solutions Architect professional. Over the years, I’ve worked across architecture, backend engineering, and technical leadership roles — helping teams design resilient systems that can scale reliably.\n\nOutside of tech, I enjoy traveling, basketball, discovering good food, and spending time with animals.",
            linkedin = "https://www.linkedin.com/in/ivan-jan%C4%8Di%C4%87/",
        ),
        "Jason Desrosiers" to SpeakerDetails(
            title = "Director",
            company = "JSON Schema TSC and Hyperjump, LLC",
            bio = "Jason Desrosiers is the creator of Hyperjump, an ecosystem of JSON Schema tools. At its center is @hyperjump/json-schema, a validator and linter for every dialect from draft-04 to JSON Schema v1. Jason has been active in the JSON Schema community for over a decade, from answering questions and maintaining the test suite to developing the specification, and now serves on its Technical Steering Committee (TSC). Jason builds tools that understand what a schema means, and shares the building blocks so others can build their own.",
            linkedin = "https://www.linkedin.com/in/jason-desrosiers/",
        ),
        "Jean-Pierre Dijcks" to SpeakerDetails(
            title = "Product Manager",
            company = "Oracle",
            bio = "JP Dijcks, short for Jean-Pierre, is a Distinguished Product Manager for Oracle Autonomous AI JSON Database and related Oracle JSON technologies. He has spent much of his career helping customers modernize data platforms across data integration, governance, data warehousing, big data, cloud, and AI.",
            linkedin = "https://www.linkedin.com/in/jdijcks/",
        ),
        "Lisa Dusseault" to SpeakerDetails(
            title = "CTO",
            company = "Data Transfer Initiative",
            bio = "Lisa Dusseault is the CTO of the non-profit Data Transfer Initiative, supporting consumer data portability across tech platforms. A dual career in standards and startups has included being founder, CTO or VP Eng of several tech startups, and several standards writing/organizing credits such as co-author of CalDAV, editor of WebDAV, chair of the XMPP and IMAPExt working groups, and Area Director for Applications area work at the IETF. Today she's focused on JSON Schema standards and how to get the AI industry to support secure personal data portability.",
            linkedin = "https://www.linkedin.com/in/lisadusseault/",
        ),
        "Lukasz Gornicki" to SpeakerDetails(
            title = "Open Source Maintainer",
            company = "Brain Fart Services",
            bio = null,
            linkedin = "https://www.linkedin.com/in/lukasz-gornicki-a621914/",
        ),
        "Matthew Adams" to SpeakerDetails(
            title = null,
            company = null,
            bio = null,
            linkedin = null,
        ),
        "Miguel Quintero" to SpeakerDetails(
            title = null,
            company = "Adapt",
            bio = null,
            linkedin = null,
        ),
        "Naresh Jain" to SpeakerDetails(
            title = "Founder",
            company = "Specmatic",
            bio = null,
            linkedin = "https://www.linkedin.com/in/nareshjain/",
        ),
        "Pascal Senn" to SpeakerDetails(
            title = "COO",
            company = "Chillicream",
            bio = "I'm co-founder of ChilliCream, where we're passionate about advancing the GraphQL ecosystem. We develop and maintain open-source software, actively help and participate in the community, and create tools that help developers to get the most out of their GraphQL APIs. Since 2025, I’ve also been a member of the GraphQL Technical Steering Committee.",
            linkedin = "https://www.linkedin.com/in/pascal-senn-90899a15a/",
        ),
        "Pubudu Gunatilaka" to SpeakerDetails(
            title = "Director & Head of Architecture, API Platform BU",
            company = "WSO2",
            bio = null,
            linkedin = "https://www.linkedin.com/in/pubudugunatilaka/",
        ),
        "Reuven Harrison" to SpeakerDetails(
            title = null,
            company = "oasdiff",
            bio = null,
            linkedin = null,
        ),
        "Simon Harrer" to SpeakerDetails(
            title = "CEO",
            company = "Entropy Data",
            bio = null,
            linkedin = "https://www.linkedin.com/in/simonharrer/",
        ),
        "Thulisile Sibanda" to SpeakerDetails(
            title = "Senior Community Manager",
            company = "AsyncAPI Initiative",
            bio = "I build and support Open Source programs and communities, the kind where contributors feel welcome, documentation makes sense, and people want to stick around.\n\nI wear many hats: Maintainer, Contributor, Program Manager, Documentation Writer, Community Advocate, and occasional data person. What ties it all together? Helping individuals do their best work in Open Source.",
            linkedin = "https://www.linkedin.com/in/v-thulisile-sibanda/",
        ),
        "Željko Kozina" to SpeakerDetails(
            title = "Technical Architect",
            company = "Chronomill",
            bio = "Željko has spent over 15 years in IT, mostly consulting for banks and insurance companies, with a focus on modernising legacy systems. Along the way, he developed deep expertise in JVM-based technologies and has become a go-to voice on topics such as API design. When he's not untangling someone else's technical debt, he's happily creating his own through a growing collection of personal projects he enthusiastically starts and occasionally finishes. Željko is passionate about sharing knowledge in a way that actually sticks, which usually means keeping things practical, a little entertaining, and free of unnecessary jargon.",
            linkedin = "https://www.linkedin.com/in/zkozina/",
        ),
    )
}

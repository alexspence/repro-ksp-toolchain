package example.annoation.processor

import com.google.auto.service.AutoService
import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.getAnnotationsByType
import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSVisitorVoid
import com.google.devtools.ksp.validate
import example.annotation.Metrics
import java.io.OutputStreamWriter

class ExampleProcessor(
    private val options: Map<String, String>,
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger
) : SymbolProcessor {
    override fun process(resolver: Resolver): List<KSAnnotated> {
        logger.info("KSP - Metrics Annotation Processor")

        val metricsSymbols = resolver.getSymbolsWithAnnotation(Metrics::class.qualifiedName!!)

        val (ableToProcess, unableToProcess) = metricsSymbols.partition { it.validate() }

        ableToProcess.filterIsInstance<KSClassDeclaration>()
            .forEach { it.accept(Visitor(), Unit) }

        return unableToProcess.toList()

    }

    private inner class Visitor() : KSVisitorVoid() {

        @OptIn(KspExperimental::class)
        override fun visitClassDeclaration(classDeclaration: KSClassDeclaration, data: Unit) {
            val metricsAnnotation = classDeclaration.getAnnotationsByType(Metrics::class).first()

            codeGenerator.createNewFile(
                Dependencies(false, classDeclaration.containingFile!!),
                packageName = classDeclaration.packageName.asString(),
                metricsAnnotation.name
                ).use { fileStream ->

                OutputStreamWriter(fileStream).use { writer ->
                    writer.appendLine("class ${metricsAnnotation.name} {}")
                }
            }
        }
    }
}

@AutoService(SymbolProcessorProvider::class)
class ExampleProcessorProvider : SymbolProcessorProvider {

    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
        return ExampleProcessor(
            options = environment.options,
            codeGenerator = environment.codeGenerator,
            logger = environment.logger
        )
    }
}

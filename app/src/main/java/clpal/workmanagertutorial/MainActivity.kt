package clpal.workmanagertutorial

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import clpal.workmanagertutorial.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val constraints =
            Constraints.Builder().setRequiredNetworkType(NetworkType.UNMETERED).build()
        val oneTimeWorkRequest =
            OneTimeWorkRequest.Builder(FirstWorker::class.java).setConstraints(constraints).build()
        val secondWorkRequest =
            OneTimeWorkRequest.Builder(SecondWorker::class.java).setConstraints(constraints).build()


        binding.startWork.setOnClickListener {
            WorkManager.getInstance(this).enqueue(oneTimeWorkRequest)

        }
        binding.startWorkSequential.setOnClickListener {
            WorkManager.getInstance(this).beginWith(oneTimeWorkRequest).then(secondWorkRequest)
                .enqueue()
        }
        val periodicWorkRequest=PeriodicWorkRequest.Builder(FirstWorker::class.java,13,TimeUnit.MINUTES).addTag("first_work").build()
        binding.startWorkPeriodic.setOnClickListener {
            WorkManager.getInstance(this).enqueueUniquePeriodicWork("first_work",ExistingPeriodicWorkPolicy.REPLACE,periodicWorkRequest)
        }
    }
}